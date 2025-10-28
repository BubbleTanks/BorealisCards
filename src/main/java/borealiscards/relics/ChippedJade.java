package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static borealiscards.BorealisCards.makeID;

public class ChippedJade extends BaseRelic {
    public static final String ID = makeID(ChippedJade.class.getSimpleName());

    public ChippedJade() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.costForTurn >= 3 && grayscale == false) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            addToBot(new GainEnergyAction(2));
            addToBot(new DrawCardAction(2));
            grayscale = true;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}