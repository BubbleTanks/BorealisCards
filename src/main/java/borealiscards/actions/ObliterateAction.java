package borealiscards.actions;

import borealiscards.SpireFields.ThisStupidFuckingExotic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ObliterateAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;

    public ObliterateAction(AbstractCreature target, DamageInfo info, AbstractCard card) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
        this.card = card;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED && (this.target != null || card.upgraded)) {
            if(!card.upgraded) {

                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
                this.target.damage(this.info);
            } else {
                boolean playedMusic = false;
                int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

                for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
                    AbstractMonster target3 = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(target3.hb.cX, target3.hb.cY, AttackEffect.NONE));
                    target3.damage(this.info);
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();

        if (this.isDone) {
            for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
                AbstractMonster target2 = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (((((AbstractMonster)target2).isDying || target2.currentHealth <= 0) && !target2.halfDead) && !ThisStupidFuckingExotic.BehaveYourself.stopFuckingPlaying.get(target2)) {

                    ThisStupidFuckingExotic.BehaveYourself.stopFuckingPlaying.set(target2, true);

                    AbstractCard tmp = card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = card.current_x;
                    tmp.current_y = card.current_y;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float)Settings.HEIGHT / 2.0F;

                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, true, false, true));
                }
            }
        }
    }
}
