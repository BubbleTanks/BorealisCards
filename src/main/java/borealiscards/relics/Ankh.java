package borealiscards.relics;

import basemod.helpers.RelicType;

import static borealiscards.BorealisCards.makeID;

public class Ankh extends BaseRelic {
    public static final String ID = makeID(Ankh.class.getSimpleName());

    public Ankh() {
        super(ID, RelicTier.RARE, LandingSound.CLINK);
        relicType = RelicType.PURPLE;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}