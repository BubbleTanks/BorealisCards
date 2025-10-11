package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.powers.CruelSunPower;
import borealiscards.powers.DoubleCardsPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CruelSun extends BaseCard {
    public static final String ID = makeID(CruelSun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            4
    );

    public CruelSun() {
        super(ID, info);
        setEthereal(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DoubleCardsPower(p)));
        addToBot(new ApplyPowerAction(p, p, new CruelSunPower(p)));
    }
}
