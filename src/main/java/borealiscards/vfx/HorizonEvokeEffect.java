package borealiscards.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class HorizonEvokeEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private ArrayList<AbstractGameEffect> purgeEffects;

    public HorizonEvokeEffect(float x, float y, ArrayList<AbstractGameEffect> e) {
        this.x = x;
        this.y = y;
        this.duration = this.startingDuration = 0.5F;
        this.purgeEffects = e;
    }

    public void update() {
        if (duration == startingDuration) {
            AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleEpicenter(this.x, this.y));
            for (int i = 0; i < 3; ++i) {
                AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleSpiralLight(this.x, this.y));
            }
            AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleSpiralHeavy(this.x, this.y));
            for (int i = 0; i < 15; ++i) {
                AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleFloaters(this.x, this.y));
            }
            AbstractDungeon.effectsQueue.add(new HorizonEvokeParticleBrilliantCenter(this.x, this.y));
        }

        duration -= Gdx.graphics.getDeltaTime();

        if (duration <= 0.0F) {
            for (AbstractGameEffect e : purgeEffects) {
                e.isDone = true;
            }
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
