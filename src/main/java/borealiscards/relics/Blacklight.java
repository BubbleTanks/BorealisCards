package borealiscards.relics;

import basemod.helpers.RelicType;

import static borealiscards.BorealisCards.makeID;

public class Blacklight extends BaseRelic {
    public static final String ID = makeID(Blacklight.class.getSimpleName());

    public Blacklight() {
        super(ID, RelicTier.SHOP, LandingSound.HEAVY);
        relicType = RelicType.GREEN;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}