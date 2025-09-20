package borealiscards.cards;

import borealiscards.powers.CruelSunPower;
import borealiscards.powers.DoubleAttacksPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.WrathStance;

public class CruelSun extends BaseCard {
    public static final String ID = makeID(CruelSun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public CruelSun() {
        super(ID, info);
        setExhaust(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
        addToBot(new ApplyPowerAction(p, p, new DoubleAttacksPower(p, 1)));
        addToBot(new ApplyPowerAction(p, p, new CruelSunPower(p)));
    }
}
