package borealiscards.cards;

import borealiscards.powers.StargazerPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Stargazer extends BaseCard {
    public static final String ID = makeID(Stargazer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Stargazer() {
        super(ID, info);
        setInnate(false,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StargazerPower(p,1)));
    }
}

// wawa :3
