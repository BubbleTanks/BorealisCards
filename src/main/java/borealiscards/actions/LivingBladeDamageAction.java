package borealiscards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LivingBladeDamageAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;

    public LivingBladeDamageAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.BLUNT_LIGHT;
        this.duration = 0.01F;
    }

    public void update() {
        if (this.duration == 0.01F && this.target != null && this.target.currentHealth > 0) {
            if (this.info.type != DamageInfo.DamageType.THORNS && this.info.owner.isDying) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
        if (this.isDone && this.target != null && this.target.currentHealth > 0) {
            this.target.damage(this.info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        if (this.target == null) {
            this.isDone = true;
        }

    }
}
