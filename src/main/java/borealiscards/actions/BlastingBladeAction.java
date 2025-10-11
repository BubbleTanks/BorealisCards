package borealiscards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlastingBladeAction extends AbstractGameAction {
    private DamageInfo info;
    private final int magic;
    private final int damage;
    private final AbstractPlayer p;
    private final AbstractMonster m;

    public BlastingBladeAction(AbstractCreature target, DamageInfo info, int magic, int damage, AbstractPlayer p, AbstractMonster m) {
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.info = info;
        this.magic = magic;
        this.damage = damage;
        this.p = p;
        this.m = m;
    }

    public void update() {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if(AbstractDungeon.player.currentBlock < 7 && AbstractDungeon.player.currentBlock > 0) {
            addToBot(new DamageAction(p, new DamageInfo(p, magic, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else addToBot(new DamageAction(p, new DamageInfo(p, magic, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        this.isDone = true;
    }
}