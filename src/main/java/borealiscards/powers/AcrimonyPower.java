package borealiscards.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class AcrimonyPower extends BasePower {
    public static final String POWER_ID = makeID(AcrimonyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private static float cardsHand() {
        return 1 + (AbstractDungeon.player.hand.group.size() * 0.05F);
    }

    public AcrimonyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return damage * cardsHand();
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