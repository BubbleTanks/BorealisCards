package borealiscards.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class StarshowerEffect extends AbstractGameEffect {
    private int frostCount;
    private boolean flipped = false;

    public StarshowerEffect(int frostCount, boolean flipped) {
        this.frostCount = 5 + frostCount;
        this.flipped = flipped;
        if (this.frostCount > 50) {
            this.frostCount = 50;
        }

    }

    public void update() {
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, true);
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.VIOLET.cpy()));

        for(int i = 0; i < this.frostCount; ++i) {
            AbstractDungeon.effectsQueue.add(new FallingStarsEffect(this.frostCount, this.flipped));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}