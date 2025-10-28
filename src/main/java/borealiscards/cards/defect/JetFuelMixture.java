package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
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
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            5
    );

    public JetFuelMixture() {
        super(ID, info);
        setEthereal(true,false);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new JetFuelPower(p, magicNumber)));
    }
}

// wawa :3
