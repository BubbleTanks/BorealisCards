package borealiscards.powers;

import borealiscards.actions.FoolhardyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class FoolhardyPower extends BasePower {
    public static final String POWER_ID = makeID(FoolhardyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public FoolhardyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        amount2 = 0;
    }

    public void onGainedBlock(float blockAmount) {
        amount2 += (int)blockAmount;
        while(amount2 >= 10) {
            addToBot(new FoolhardyAction(amount));
            amount2 -= 10;
            flash();
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}