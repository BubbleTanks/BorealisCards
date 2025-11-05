package borealiscards.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

import static borealiscards.BorealisCards.makeID;

public class AntennaHeadband extends BaseRelic {
    public static final String ID = makeID(AntennaHeadband.class.getSimpleName());

    public AntennaHeadband() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        if (room instanceof RestRoom) {
            beginLongPulse();
        } else stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}