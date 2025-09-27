package borealiscards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class CausticsPower extends BasePower {
    public static final String POWER_ID = makeID(CausticsPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public CausticsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cardID == Shiv.ID && action.target != null) {
            addToBot(new ApplyPowerAction(action.target, owner, new AcrimonyPower(action.target, 1), 1, true));
            flash();
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