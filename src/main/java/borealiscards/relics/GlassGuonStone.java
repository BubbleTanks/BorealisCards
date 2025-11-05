package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static borealiscards.BorealisCards.makeID;

public class GlassGuonStone extends BaseRelic {
    public static final String ID = makeID(GlassGuonStone.class.getSimpleName());

    public GlassGuonStone() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void onPlayerEndTurn() {
        if(!grayscale) {
            this.flash();
            this.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void wasHPLost(int i) {
        grayscale = true;
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