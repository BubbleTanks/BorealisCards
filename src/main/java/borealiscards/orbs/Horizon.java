package borealiscards.orbs;

import basemod.abstracts.CustomOrb;
import borealiscards.vfx.HorizonEvokeEffect;
import borealiscards.vfx.HorizonParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import java.util.ArrayList;

import static borealiscards.BorealisCards.*;

public class Horizon extends CustomOrb {
    public static String ORB_ID = makeID(Horizon.class.getSimpleName());
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private float vfxTimer = 1.0F;
    private final float vfxIntervalMin = 0.02F;
    private final float vfxIntervalMax = 0.04F;
    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.566371F;
    private ArrayList<AbstractGameEffect> purgeEffects = new ArrayList<>();

    public Horizon() {
        super(
                ORB_ID,
                orbString.NAME,
                1,
                1,
                orbString.DESCRIPTION[0],
                orbString.DESCRIPTION[1],
                imagePath("orbs/Horizon.png")
        );
        angle = MathUtils.random(360.0F);
        channelAnimTimer = 0.5F;
    }

    public void applyFocus() {
        passiveDescription = String.format(orbString.DESCRIPTION[0],passiveAmount);
        evokeDescription = String.format(orbString.DESCRIPTION[1],evokeAmount);
    }

    public void onEvoke() {

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (o.ID != Horizon.ORB_ID) {
                        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                            @Override
                            public void update() {
                                o.onEvoke();
                                o.triggerEvokeAnimation();
                                this.isDone = true;
                            }
                        });
                    }
                }
                this.isDone = true;
            }
        });
    }

    public void onStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player,1)));
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play(HORIZON_EVOKE_SFX_KEY, 0.1F);
        AbstractDungeon.effectsQueue.add(new HorizonEvokeEffect(cX, cY, purgeEffects));
    }

    public void updateAnimation() {
        super.updateAnimation();
        AbstractGameEffect horizonParticle = new HorizonParticle(this);
        purgeEffects.add(horizonParticle);
        angle += Gdx.graphics.getDeltaTime() * 45.0F;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(horizonParticle);
            ArrayList<AbstractGameEffect> eKill = new ArrayList<>();
            for (AbstractGameEffect e : purgeEffects) {
                if (e.isDone) {
                    eKill.add(e);
                }
            }
            for (AbstractGameEffect e : eKill) {
                purgeEffects.remove(e);
            }
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    public void render(SpriteBatch sb) {
        shineColor.a = c.a / 1.2F;
        sb.setColor(shineColor);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale + MathUtils.sin(angle / 12.566371F) * 0.04F * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale, scale + MathUtils.sin(angle / 12.566371F) * 0.04F * Settings.scale, -angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.playV(HORIZON_CHANNEL_SFX_KEY, 3.0F);
    }

    public AbstractOrb makeCopy() {
        return new Horizon();
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString(Horizon.ORB_ID);
        DESC = orbString.DESCRIPTION;
    }
}
