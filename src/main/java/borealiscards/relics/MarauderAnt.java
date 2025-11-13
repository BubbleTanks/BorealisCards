package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class MarauderAnt extends BaseRelic {
    public static final String ID = makeID(MarauderAnt.class.getSimpleName());
    private boolean hasPlayedAttack = false;

    public MarauderAnt() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void atPreBattle() {
        hasPlayedAttack = false;
        grayscale = false;
    }

    public void atTurnStart() {
        hasPlayedAttack = false;
        flash();
        grayscale = false;
    }

    public void onPlayerEndTurn() {
        if (!hasPlayedAttack) {
            addToBot(new GainBlockAction(AbstractDungeon.player, 8));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            hasPlayedAttack = true;
            flash();
            grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}