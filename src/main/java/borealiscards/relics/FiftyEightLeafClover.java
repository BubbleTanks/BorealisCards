package borealiscards.relics;

import static borealiscards.BorealisCards.makeID;

public class FiftyEightLeafClover extends BaseRelic {
    public static final String ID = makeID(FiftyEightLeafClover.class.getSimpleName());

    public FiftyEightLeafClover() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}