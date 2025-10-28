package borealiscards.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LivingBladeEffect extends AbstractGameAction {
    public LivingBladeEffect(AbstractMonster m, Color color) {
        this.source = m;
        this.color = color;
        color2 = new Color(color.r - 0.4F, color.g - 0.4F, color.b - 0.4F, color.a);
    }

    private final Color color;
    private final Color color2;

    public void update() {
        if (!this.source.isDeadOrEscaped()) {
            float randomAngle = MathUtils.random(360.0F);
            //float randomAngle = 90.0F;
            float randomX = (float) MathUtils.sinDeg(randomAngle) * -500;
            float randomY = (float) MathUtils.cosDeg(randomAngle) * 500;
            this.addToTop(new VFXAction(new LivingSlashEffect(this.source.hb.cX, this.source.hb.cY - 30.0F * Settings.scale, randomX, randomY, randomAngle, 4.0F, color.cpy(), color2.cpy())));
            this.addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.7F, true));
            this.addToTop(new SFXAction("ATTACK_IRON_3", 0.2F));
        }

        this.isDone = true;
    }
}
