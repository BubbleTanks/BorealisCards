package borealiscards.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static borealiscards.BorealisCards.makeID;

public class TachycardiaPower extends BasePower {
    public static final String POWER_ID = makeID(TachycardiaPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public TachycardiaPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower fuckKnuckles : m.powers) {
                if (fuckKnuckles.ID == AcrimonyPower.POWER_ID) {
                    addToBot(new ApplyPowerAction(m, owner, new PoisonPower(m, owner, fuckKnuckles.amount), fuckKnuckles.amount, AbstractGameAction.AttackEffect.POISON));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}