package borealiscards.actions;

import borealiscards.powers.FracturePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpinebusterAction extends AbstractGameAction {
    private DamageInfo info;
    private final int magic;
    private final AbstractPlayer p;
    private final AbstractMonster m;

    public SpinebusterAction(AbstractCreature target, DamageInfo info, int magic, AbstractPlayer p, AbstractMonster m) {
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.info = info;
        this.magic = magic;
        this.p = p;
        this.m = m;
    }

    public void update() {
        if (this.target != null && this.target.hasPower("Vulnerable")) {
            addToBot(new ApplyPowerAction(m, p, new FracturePower(m, magic), magic));
        }
        this.isDone = true;
    }
}