package borealiscards.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HorizonEvokeEffect extends AbstractGameEffect {
    private float x;
    private float y;

    public HorizonEvokeEffect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleEpicenter(this.x, this.y));
        for(int i = 0; i < 3; ++i) {
            AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleSpiralLight(this.x, this.y));
        }
        AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleSpiralHeavy(this.x, this.y));
        for(int i = 0; i < 15; ++i) {
            AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleFloaters(this.x, this.y));
        }
        AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleBrilliantCenter(this.x, this.y));
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
