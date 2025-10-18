package borealiscards.relics;

import static borealiscards.BorealisCards.makeID;

public class BonfireHearth extends BaseRelic {
    public static final String ID = makeID(BonfireHearth.class.getSimpleName());

    public BonfireHearth() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}