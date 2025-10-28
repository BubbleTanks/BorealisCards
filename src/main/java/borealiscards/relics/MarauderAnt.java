package borealiscards.relics;

import static borealiscards.BorealisCards.makeID;

public class MarauderAnt extends BaseRelic {
    public static final String ID = makeID(MarauderAnt.class.getSimpleName());

    public MarauderAnt() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}