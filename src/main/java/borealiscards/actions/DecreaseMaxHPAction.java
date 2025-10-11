package borealiscards.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;

public class DecreaseMaxHPAction extends AbstractGameAction {
    private float decreaseAmount;

    public DecreaseMaxHPAction(AbstractPlayer p, float decreaseAmount) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.decreaseAmount = decreaseAmount;
        this.target = p;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.decreaseMaxHealth(MathUtils.round(this.decreaseAmount));
        }

        this.tickDuration();
    }
}
