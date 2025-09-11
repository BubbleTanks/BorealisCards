package borealiscards.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StarlightEvokeParticle extends AbstractGameEffect {
    private float dur_div2 = this.duration / 2.0F;

    private float vX;
    private float vY;
    private float x;
    private float y;

    private float dvx = 1.0F * Settings.scale * this.scale;
    private float dvy = 25.0F * Settings.scale;


    public StarlightEvokeParticle(float x, float y) {
        this.img = ImageMaster.TINY_STAR;
        this.duration = 0.5F;
        this.scale = MathUtils.random(1.3F, 1.8F) * Settings.scale;
        this.dur_div2 = this.duration / 2.0F;
        this.color = Color.ROYAL.cpy();
        this.color.a = 0.0F;
        this.renderBehind = MathUtils.randomBoolean(0.2F + this.scale - 0.5F);
        this.rotation = MathUtils.random(360.0F, 0.0F);
        this.y = y + MathUtils.random(-35.0F, -5.0F) * Settings.scale;
        this.x = x + MathUtils.random(-30.0F, -10.0F) * Settings.scale;
        vX = MathUtils.random(300.0F, -300.0F) * Settings.scale;
        vY = MathUtils.random(30.0F, -30.0F) * Settings.scale;

    }
    private TextureAtlas.AtlasRegion img;

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.vY += Gdx.graphics.getDeltaTime() * this.dvy;
        this.vX -= Gdx.graphics.getDeltaTime() * this.dvx;

        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.dur_div2);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw((TextureRegion)this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.8F, this.scale * 0.8F, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}