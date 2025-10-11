package borealiscards.relics;

import basemod.helpers.RelicType;
import borealiscards.orbs.Starlight;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static borealiscards.BorealisCards.makeID;

public class FadedStarmap extends BaseRelic {
    public static final String ID = makeID(FadedStarmap.class.getSimpleName());

    public FadedStarmap() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        relicType = RelicType.BLUE;
    }

    public void onPlayerEndTurn() {
        boolean stars = false;
        if(AbstractDungeon.player.maxOrbs > 0) for(AbstractOrb o : AbstractDungeon.player.orbs) {
            if(o.ID == Starlight.ORB_ID) {
                stars = true;
                break;
            }
        }
        if(!stars) {
            addToBot(new ChannelAction(new Starlight()));
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}