package borealiscards.cards;

import borealiscards.powers.RondelmancyPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Rondelmancy extends BaseCard {
    public static final String ID = makeID(Rondelmancy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Rondelmancy() {
        super(ID, info);
        cardsToPreview = new Shiv();
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RondelmancyPower(p, this.magicNumber), this.magicNumber));
    }
};
