package borealiscards.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LivingWindEffect extends AbstractGameEffect {
    private int count;
    private float timer;
    private boolean reverse;

    public LivingWindEffect(Color setColor, boolean reverse) {
        this.count = 0;
        this.timer = 0.0F;
        this.reverse = false;
        this.color = setColor.cpy();
        this.reverse = reverse;
    }

    public LivingWindEffect() {
        this(new Color(0.9F, 0.9F, 1.0F, 1.0F), false);
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += 0.05F;
            if (this.count == 0) {
                AbstractDungeon.effectsQueue.add(new LivingFlashEffect(this.color.cpy()));
            }

            AbstractDungeon.effectsQueue.add(new LivingWindyParticleEffect(this.color, this.reverse));
            ++this.count;
            if (this.count == 8) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
