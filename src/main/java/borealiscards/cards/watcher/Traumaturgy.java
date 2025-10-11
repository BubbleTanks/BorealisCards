package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.powers.TraumaturgyPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Traumaturgy extends BaseCard {
    public static final String ID = makeID(Traumaturgy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    public Traumaturgy() {
        super(ID, info);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TraumaturgyPower(p)));
    }
}
