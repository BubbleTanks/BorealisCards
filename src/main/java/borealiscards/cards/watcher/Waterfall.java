package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.powers.WaterfallPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Waterfall extends BaseCard {
    public static final String ID = makeID(Waterfall.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Waterfall() {
        super(ID, info);
        setMagic(3,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WaterfallPower(p, magicNumber), magicNumber));
    }
}
