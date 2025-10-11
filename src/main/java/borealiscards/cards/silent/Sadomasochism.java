package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.powers.SadomasochismPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sadomasochism extends BaseCard {
    public static final String ID = makeID(Sadomasochism.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Sadomasochism() {
        super(ID, info);
        setMagic(1,0);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SadomasochismPower(p, magicNumber), magicNumber));
    }
}
