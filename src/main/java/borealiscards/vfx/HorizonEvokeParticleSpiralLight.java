package borealiscards.vfx;

import borealiscards.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HorizonEvokeParticleSpiralLight extends AbstractGameEffect {
    private float dur_div2 = this.duration / 2.0F;

    private float vX;
    private float vY;
    private float vR;
    private float x;
    private float y;

    private float dvx = 1.0F * Settings.scale * this.scale;
    private float dvy = 1.0F * Settings.scale;

    public static Texture EPICENTER_IMG = TextureLoader.getTexture("borealiscards/images/vfx/horizon_spiral.png");
            // ImageMaster.INTENT_ESCAPE_L;

            //

    public HorizonEvokeParticleSpiralLight(float x, float y) {
        this.img = EPICENTER_IMG;
        this.duration = 1.0F;
        this.scale = MathUtils.random(2.0F, 2.0F) * Settings.scale;
        this.dur_div2 = this.duration / 2.0F;
        this.color = Color.WHITE.cpy();
        this.color.a = 0.0F;
        this.renderBehind = false;
        this.rotation = MathUtils.random(0.0F, 360.0F);
        this.vR = MathUtils.random(-25.0F, -50.0F);
        this.y = y * Settings.scale;
        this.x = x * Settings.scale;
        vX = MathUtils.random(0.0F, 0.0F) * Settings.scale;
        vY = MathUtils.random(0.0F, 0.0F) * Settings.scale;

    }
    private Texture img;

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.vY += Gdx.graphics.getDeltaTime() * this.dvy;
        this.vX -= Gdx.graphics.getDeltaTime() * this.dvx;
        this.rotation += this.vR * Gdx.graphics.getDeltaTime();

         if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(0.05F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 0.05F, this.duration / this.dur_div2);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x - 75.0F, this.y - 75.0F, 75.0F, 75.0F, 150.0F, 150.0F, this.scale, this.scale, this.rotation, 0, 0, 150, 150, false,false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}