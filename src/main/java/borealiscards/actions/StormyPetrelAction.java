package borealiscards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class StormyPetrelAction extends AbstractGameAction {

    public void update() {
        if (AbstractDungeon.player.stance.ID.equals(CalmStance.STANCE_ID)) {
            this.addToTop(new ChangeStanceAction(WrathStance.STANCE_ID));
        } else if (AbstractDungeon.player.stance.ID.equals(WrathStance.STANCE_ID)) {
        this.addToTop(new ChangeStanceAction(CalmStance.STANCE_ID));
        }

        this.isDone = true;
    }
}