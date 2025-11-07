package borealiscards.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class ElectricBaghnakhsPower extends BasePower {
    public static final String POWER_ID = makeID(ElectricBaghnakhsPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ElectricBaghnakhsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.costForTurn == 0) {
            return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}