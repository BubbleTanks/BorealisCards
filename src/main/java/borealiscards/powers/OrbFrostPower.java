package borealiscards.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;

import static borealiscards.BorealisCards.makeID;

public class OrbFrostPower extends BasePower {
    public static final String POWER_ID = makeID(OrbFrostPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static final AbstractOrb FROSTORB = new Frost();

    public OrbFrostPower(AbstractCreature owner, int amount) {super(POWER_ID, TYPE, TURN_BASED, owner, amount);}

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        for (int i = 0; i < amount; i++) {
            FROSTORB.onEndOfTurn();
            flash();
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}