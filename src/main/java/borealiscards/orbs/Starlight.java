package borealiscards.orbs;

import basemod.abstracts.CustomOrb;
import borealiscards.vfx.StarlightEvokeEffect;
import borealiscards.vfx.StarlightParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import static borealiscards.BorealisCards.imagePath;
import static borealiscards.BorealisCards.makeID;

public class Starlight extends CustomOrb {
    public static String ORB_ID = makeID(Starlight.class.getSimpleName());
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private float vfxTimer = 1.0F;
    private final float vfxIntervalMin = 0.05F;
    private final float vfxIntervalMax = 0.2F;
    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.566371F;

    public Starlight() {
        super(
                ORB_ID,
                orbString.NAME,
                2,
                1,
                orbString.DESCRIPTION[0],
                orbString.DESCRIPTION[1],
                imagePath("orbs/Starlight.png")
        );
        angle = MathUtils.random(360.0F);
        channelAnimTimer = 0.5F;
    }

    public void applyFocus() {
        passiveDescription = String.format(orbString.DESCRIPTION[0],passiveAmount);
        evokeDescription = String.format(orbString.DESCRIPTION[1],evokeAmount);
    }

    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(evokeAmount));
    }

    public void onStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.1F);
        AbstractDungeon.effectsQueue.add(new StarlightEvokeEffect(cX, cY));
    }

    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0F;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new StarlightParticle(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    public void render(SpriteBatch sb) {
        shineColor.a = c.a / 2.0F;
        sb.setColor(shineColor);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale + MathUtils.sin(angle / 12.566371F) * 0.04F * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale, scale + MathUtils.sin(angle / 12.566371F) * 0.04F * Settings.scale, -angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("SELECT_WATCHER", 0.1F);
    }

    public AbstractOrb makeCopy() {
        return new Starlight();
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString(Starlight.ORB_ID);
        DESC = orbString.DESCRIPTION;
    }
}
