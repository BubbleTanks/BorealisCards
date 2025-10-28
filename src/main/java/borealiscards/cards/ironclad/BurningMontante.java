package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.BurningMontantePower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurningMontante extends BaseCard {
    public static final String ID = makeID(BurningMontante.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.POWER,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            2
    );

    public BurningMontante() {
        super(ID, info);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BurningMontantePower(p, magicNumber), magicNumber));
    }
}
