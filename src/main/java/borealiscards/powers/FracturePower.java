package borealiscards.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class FracturePower extends BasePower {
    public static final String POWER_ID = makeID(FracturePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public FracturePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 4;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage + amount;
        } else {
            return damage;
        }
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
        if(amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}