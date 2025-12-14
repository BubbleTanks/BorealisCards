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
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HorizonParticle extends AbstractGameEffect {
    private float dur_div2 = this.duration / 2.0F;

    private float vX;
    private float vY;
    private float x;
    private float y;
    private float oX;
    private float oY;

    private AbstractOrb horizon;

    private float dvx = 1.0F * Settings.scale * this.scale;
    private float dvy = -10.0F * Settings.scale;


    public HorizonParticle(AbstractOrb o) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.duration = MathUtils.random(3.2F, 3.7F);
        this.scale = MathUtils.random(0.2F, 0.5F) * Settings.scale;
        this.dur_div2 = this.duration / 2F;
        this.horizon = o;
        int tmp = MathUtils.random(4);
        if (tmp == 0) {
            this.color = Color.ORANGE.cpy();
        } else {
            this.color = Color.WHITE.cpy();
        }
        this.renderBehind = MathUtils.randomBoolean(0.2F + this.scale - 0.5F);
        this.rotation = MathUtils.random(360.0F, 0.0F);
        this.oY = MathUtils.random(-30.0F, -27.0F) * Settings.scale;
        this.oX = MathUtils.random(-100.0F, -90.0F) * Settings.scale;
        this.y = horizon.cY + oY * Settings.scale;
        this.x = horizon.cX + oX * Settings.scale;
        vX = MathUtils.random(30.0F, 35.0F) * Settings.scale;
        vY = (MathUtils.random(-3.0F, 3.0F) - 18.0F) * Settings.scale;

    }
    private TextureAtlas.AtlasRegion img;

    public void update() {
        this.oX += this.vX * Gdx.graphics.getDeltaTime();
        this.oY += this.vY * Gdx.graphics.getDeltaTime();
        this.vY -= Gdx.graphics.getDeltaTime() * this.dvy;
        this.vX -= Gdx.graphics.getDeltaTime() * this.dvx;
        this.x = horizon.cX + oX;
        this.y = horizon.cY + oY;

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