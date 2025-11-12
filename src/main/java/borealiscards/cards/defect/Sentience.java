package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.SentiencePower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sentience extends BaseCard {
    public static final String ID = makeID(Sentience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            2
    );

    public Sentience() {
        super(ID, info);
        setInnate(false,true);
        setEthereal(true);
        setMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SentiencePower(p, magicNumber)));
    }
}

// wawa :3
