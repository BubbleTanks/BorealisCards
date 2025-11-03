package borealiscards.actions;

import borealiscards.SpireFields.DrawField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RedrawColorCardsAction extends AbstractGameAction {
    private AbstractCard.CardColor color;

    public RedrawColorCardsAction(AbstractCard.CardColor color) {
        this.color = color;
    }

    public void update() {
        int redraw = 0;
        ArrayList<AbstractCard> cardsToDiscard = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.color == color) {
                cardsToDiscard.add(c);
                redraw++;
            }
        }
        for (AbstractCard c : cardsToDiscard) {
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
        }

        AbstractDungeon.player.hand.applyPowers();

        AbstractGameAction drawAction = new DrawCardAction(redraw);
        DrawField.HazardDraw.drawCardsFast.set(drawAction, true);
        AbstractDungeon.actionManager.addToBottom(drawAction);

        this.isDone = true;
    }
}