package borealiscards.cards;

import borealiscards.powers.CausticsPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Caustics extends BaseCard {
    public static final String ID = makeID(Caustics.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Caustics() {
        super(ID, info);
        setMagic(2,0);
        setExhaust(true,false);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Shiv(), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new CausticsPower(p, 1)));
    }
}


