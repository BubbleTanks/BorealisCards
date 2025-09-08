package borealiscards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StormyPetrelAction extends AbstractGameAction {

    public void update() {
        if (AbstractDungeon.player.stance.ID.equals("Calm")) {
            this.addToTop(new ChangeStanceAction("Wrath"));
        } else if (AbstractDungeon.player.stance.ID.equals("Wrath")) {
            this.addToTop(new ChangeStanceAction("Calm"));
        }

        this.isDone = true;
    }
}