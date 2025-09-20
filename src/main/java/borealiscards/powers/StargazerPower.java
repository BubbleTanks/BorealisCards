package borealiscards.powers;

import borealiscards.orbs.Starlight;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class StargazerPower extends BasePower {
    public static final String POWER_ID = makeID(StargazerPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public StargazerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        for (int i = 0; i < amount; i++) {
            addToBot(new ChannelAction(new Starlight()));
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