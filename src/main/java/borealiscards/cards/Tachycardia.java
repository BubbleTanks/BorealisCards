package borealiscards.cards;

import borealiscards.powers.TachycardiaPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Tachycardia extends BaseCard {
    public static final String ID = makeID(Tachycardia.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Tachycardia() {
        super(ID, info);
        setMagic(1,0);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TachycardiaPower(p, magicNumber), magicNumber));
    }
}
