package borealiscards.relics;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import borealiscards.actions.RedrawColorCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static borealiscards.BorealisCards.makeID;

public class RadHazard extends BaseRelic {
    public static final String ID = makeID(RadHazard.class.getSimpleName());

    public RadHazard() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
        relicType = RelicType.GREEN;
    }

    private int handPenalty = 0;

    public void onUseCard(AbstractCard c, UseCardAction action) {
        addToBot(new RedrawColorCardsAction(AbstractCard.CardColor.GREEN));
    }

    public void onShuffle() {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                BaseMod.MAX_HAND_SIZE -= 1;
                handPenalty ++;
                this.isDone = true;
            }
        });
    }

    public void onPlayerEndTurn() {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                BaseMod.MAX_HAND_SIZE += handPenalty;
                handPenalty = 0;
                this.isDone = true;
            }
        });
    }

    public void onVictory() {
        BaseMod.MAX_HAND_SIZE += handPenalty;
        handPenalty = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}