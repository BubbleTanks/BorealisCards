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

public class HorizonEvokeParticleFloaters extends AbstractGameEffect {
    private float dur_div2 = this.duration / 2.0F;

    private float vX;
    private float vY;
    private float x;
    private float y;
    private final float x2;
    private final float y2;
    private float zD;
    private float zR;
    private float zVR;


    public HorizonEvokeParticleFloaters(float x, float y) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.duration = 1.0F;
        this.scale = MathUtils.random(1.0F, 1.0F) * Settings.scale;
        this.dur_div2 = this.duration / 2.0F;
        int tmp = MathUtils.random(4);
        if (tmp == 0) {
            this.color = Color.ORANGE.cpy();
        } else {
            this.color = Color.WHITE.cpy();
        }
        this.color.a = 0.0F;
        this.renderBehind = false;
        this.rotation = MathUtils.random(0.0F, 360.0F);
        this.y = y * Settings.scale;
        this.x = x * Settings.scale;
        this.y2 = y * Settings.scale;
        this.x2 = x * Settings.scale;
        this.zD = MathUtils.random(30.0F, 120.0F) * Settings.scale;
        this.zR = MathUtils.random(0.0F, 360.0F);
        this.zVR = MathUtils.random(-150.0F + zD, -180.0F + zD);

    }
    private TextureAtlas.AtlasRegion img;

    public void update() {
        this.x = x2 + MathUtils.cosDeg(zR) * zD;
        this.y = y2 + MathUtils.sinDeg(zR) * zD;
        this.zR += zVR * Gdx.graphics.getDeltaTime();

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
        sb.draw((TextureRegion)this.img, this.x - (this.img.packedWidth / 2.0F), this.y - (this.img.packedHeight / 2.0F), this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.8F, this.scale * 0.8F, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}