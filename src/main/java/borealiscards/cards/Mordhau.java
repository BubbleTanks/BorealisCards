package borealiscards.cards;

import borealiscards.powers.MordhauPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Mordhau extends BaseCard {
    public static final String ID = makeID(Mordhau.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public Mordhau() {
        super(ID, info);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MordhauPower(p, magicNumber)));
    }
}
