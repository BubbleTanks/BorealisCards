package borealiscards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

public class SpiritWalkAction extends AbstractGameAction {

    public void update() {
        if (AbstractDungeon.player.stance.ID.equals(CalmStance.STANCE_ID)) {
            this.addToTop(new MakeTempCardInHandAction(new Smite()));
        } else if (AbstractDungeon.player.stance.ID.equals(WrathStance.STANCE_ID)) {
            this.addToTop(new MakeTempCardInHandAction(new Safety()));
        }

        addToBot(new NotStanceCheckAction(NeutralStance.STANCE_ID, new VFXAction(new EmptyStanceEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1F)));
        addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
        this.isDone = true;
    }
}