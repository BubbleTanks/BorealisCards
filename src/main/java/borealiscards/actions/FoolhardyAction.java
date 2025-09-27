package borealiscards.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FoolhardyAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int powerAmount;

    public FoolhardyAction(int amount) {
        actionType = ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
        powerAmount = amount;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            boolean betterPossible = false;
            boolean possible = false;

            for(AbstractCard c : p.hand.group) {
                if (c.costForTurn > 0) {
                    betterPossible = true;
                } else if (c.cost > 0) {
                    possible = true;
                }
            }

            if (betterPossible || possible) {
                findAndModifyCard(betterPossible);
            }
        }

        this.tickDuration();
    }

    private void findAndModifyCard(boolean better) {
        AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if (better) {
            if (c.costForTurn > 0) {
                c.setCostForTurn(c.costForTurn - powerAmount);
                c.superFlash(Color.GOLD.cpy());
            } else {
                findAndModifyCard(better);
            }
        } else if (c.cost > 0) {
            c.setCostForTurn(c.costForTurn - powerAmount);
            c.superFlash(Color.GOLD.cpy());
        } else {
            findAndModifyCard(better);
        }
    }
}
