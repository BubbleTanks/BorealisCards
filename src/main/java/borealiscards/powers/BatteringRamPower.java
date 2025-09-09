package borealiscards.powers;

import borealiscards.vfx.BatteringRamEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class BatteringRamPower extends BasePower {
    public static final String POWER_ID = makeID(BatteringRamPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public BatteringRamPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target != owner && info.type == DamageInfo.DamageType.NORMAL && owner != null) {
            flash();
            if (target != null) {
                addToBot(new VFXAction(new BatteringRamEffect(target.hb.cX + target.hb.width / 4.0F, target.hb.cY - target.hb.height / 4.0F)));
            }
            addToBot(new DamageAction(target, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}