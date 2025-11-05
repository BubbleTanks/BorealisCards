package borealiscards.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static borealiscards.BorealisCards.makeID;

public class SleepingButterfly extends BaseRelic {
    public static final String ID = makeID(SleepingButterfly.class.getSimpleName());
    public boolean butterflyRest = false;

    public SleepingButterfly() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void justEnteredRoom(AbstractRoom room) {
        stopPulse();
        butterflyRest = false;
    }

    public void onRest() {
        beginLongPulse();
        butterflyRest = true;
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}