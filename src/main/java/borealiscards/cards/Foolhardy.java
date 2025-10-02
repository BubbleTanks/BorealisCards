package borealiscards.cards;

import borealiscards.powers.FoolhardyPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Foolhardy extends BaseCard {
    public static final String ID = makeID(Foolhardy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Foolhardy() {
        super(ID, info);
        setMagic(1,0);
        setEthereal(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FoolhardyPower(p, magicNumber), magicNumber));
    }
}
