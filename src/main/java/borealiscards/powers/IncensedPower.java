package borealiscards.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class IncensedPower extends BasePower {
    public static final String POWER_ID = makeID(IncensedPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public IncensedPower(AbstractCreature owner) {super(POWER_ID, TYPE, TURN_BASED, owner, -1);}

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}