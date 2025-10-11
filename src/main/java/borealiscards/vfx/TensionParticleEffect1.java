package borealiscards.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TensionParticleEffect1 extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float dur_div2;
    private float dvy;
    private float dvx;
    private TextureAtlas.AtlasRegion img;

    public TensionParticleEffect1() {
        this.img = ImageMaster.GLOW_SPARK;
        this.duration = MathUtils.random(1.1F, 1.5F);
        this.scale = MathUtils.random(0.6F, 1.2F) * Settings.scale;
        this.dur_div2 = this.duration / 2.0F;
        this.color = new Color(MathUtils.random(0.5F, 1.0F), MathUtils.random(0.5F, 0.8F), MathUtils.random(0.0F, 0.1F), 0.0F);
        this.vX = MathUtils.random(170.0F, 270.0F) * Settings.scale;
        this.vY = MathUtils.random(70.0F, 120.0F) * Settings.scale;
        this.x = AbstractDungeon.player.hb.cX + MathUtils.random(-210.0F, -150.0F) * Settings.scale - 32.0F;
        this.y = AbstractDungeon.player.hb.cY + MathUtils.random(-250.0F, -190.0F) * Settings.scale - 32.0F;
        this.renderBehind = MathUtils.randomBoolean(0.2F + (this.scale - 0.5F));
        this.dvx = -30.0F * Settings.scale * this.scale;
        this.dvy = 30.0F * Settings.scale;
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.vY += Gdx.graphics.getDeltaTime() * this.dvy;
        this.vX -= Gdx.graphics.getDeltaTime() * this.dvx;
        this.rotation = -((180F / (float)Math.PI) * MathUtils.atan2(this.vX, this.vY)) - 0.0F;
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
        sb.draw(this.img, this.x, this.y + this.vY, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.8F, (0.1F + (this.dur_div2 * 2.0F - this.duration) * 2.0F * this.scale) * Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
