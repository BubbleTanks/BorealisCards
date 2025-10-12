package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.stances.TensionStance;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SilentiumGradas extends BaseCard {
    public static final String ID = makeID(SilentiumGradas.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public SilentiumGradas() {
        super(ID, info);
        setMagic(3,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new ChangeStanceAction(new TensionStance()));
    }
}
