package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.powers.HyperPropellantPower;
import borealiscards.powers.LoseFocusPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

public class HyperPropellant extends BaseCard {
    public static final String ID = makeID(HyperPropellant.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public HyperPropellant() {
        super(ID, info);
        setMagic(5,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new HyperPropellantPower(p, 1)));
    }
}
