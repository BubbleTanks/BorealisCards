package borealiscards.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class UndistortPower extends BasePower {
    public static final String POWER_ID = makeID(UndistortPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static int handBuff;

    public UndistortPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        handBuff = 99 - BaseMod.MAX_HAND_SIZE;
        BaseMod.MAX_HAND_SIZE += handBuff;
    }

    public void onRemove() {
        BaseMod.MAX_HAND_SIZE -= handBuff;
    }

    public void onVictory() {
        BaseMod.MAX_HAND_SIZE -= handBuff;
    }

    public void atEndOfRound() {
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
        } else {
            addToBot(new ReducePowerAction(owner, owner, ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}