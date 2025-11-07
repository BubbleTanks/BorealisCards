package borealiscards.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class EdgeAlignmentPower extends BasePower {
    public static final String POWER_ID = makeID(EdgeAlignmentPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public EdgeAlignmentPower(AbstractCreature owner, int amount) {super(POWER_ID, TYPE, TURN_BASED, owner, amount);}

    @Override
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}