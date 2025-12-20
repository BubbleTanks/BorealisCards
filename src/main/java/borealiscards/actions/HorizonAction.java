package borealiscards.actions;

import borealiscards.orbs.Horizon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class HorizonAction extends AbstractGameAction {

    public HorizonAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o.ID != Horizon.ORB_ID) {
                    AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            o.onEvoke();
                            o.triggerEvokeAnimation();
                            this.isDone = true;
                        }
                    });
                }
            }
        }

        this.tickDuration();
    }
}