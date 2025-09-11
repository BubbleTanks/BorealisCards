package borealiscards.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StarlightEvokeEffect extends AbstractGameEffect {
    private float x;
    private float y;

    public StarlightEvokeEffect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        AbstractDungeon.effectsQueue.add(new StarlightEvokeParticleCenter(this.x, this.y));
        for(int i = 0; i < 12; ++i) {
            AbstractDungeon.effectsQueue.add(new StarlightEvokeParticle(this.x, this.y));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
