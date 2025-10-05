package borealiscards.cards;

import borealiscards.powers.JetFuelPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JetFuelMixture extends BaseCard {
    public static final String ID = makeID(JetFuelMixture.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            5
    );

    public JetFuelMixture() {
        super(ID, info);
        setEthereal(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new JetFuelPower(p, 1)));
    }
}

// wawa :3
