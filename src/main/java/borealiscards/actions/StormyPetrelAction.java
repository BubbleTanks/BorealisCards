package borealiscards.actions;

import borealiscards.stances.TensionStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.CalmStance;

public class StormyPetrelAction extends AbstractGameAction {

    public void update() {
        if (AbstractDungeon.player.stance.ID.equals(CalmStance.STANCE_ID)) {
            this.addToTop(new ChangeStanceAction(new TensionStance()));
        } else if (AbstractDungeon.player.stance.ID.equals(TensionStance.STANCE_ID)) {
        this.addToTop(new ChangeStanceAction(CalmStance.STANCE_ID));
        }

        this.isDone = true;
    }
}