package borealiscards.stances;

import borealiscards.vfx.TensionParticleEffect1;
import borealiscards.vfx.TensionParticleEffect2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

import static borealiscards.BorealisCards.makeID;

public class TensionStance extends AbstractStance {
    public static final String STANCE_ID = makeID(TensionStance.class.getSimpleName());
    private static final UIStrings stanceString;
    private static long sfxId;

    public TensionStance() {
        ID = STANCE_ID;
        name = stanceString.TEXT[0];
        updateDescription();
    }

    public void onPlayCard(AbstractCard c) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if (particleTimer < 0.0F) {
                particleTimer = 0.1F;
                AbstractDungeon.effectsQueue.add(new TensionParticleEffect1());
                AbstractDungeon.effectsQueue.add(new TensionParticleEffect2());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (particleTimer2 < 0.0F) {
            particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect(STANCE_ID));
        }

    }

    public void updateDescription() {
        this.description = stanceString.TEXT[1];
    }

    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

        CardCrawlGame.sound.playAV("STANCE_ENTER_WRATH", -0.4F, 1.3F);
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD.cpy(), true));
    }

    public void onExitStance() {
        stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }

    }

    static {
        stanceString = CardCrawlGame.languagePack.getUIString(STANCE_ID);
        sfxId = -1L;
    }
}
