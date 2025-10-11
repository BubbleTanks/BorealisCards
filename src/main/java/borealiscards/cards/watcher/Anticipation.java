package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.stances.TensionStance;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Anticipation extends BaseCard {
    public static final String ID = makeID(Anticipation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    public Anticipation() {
        super(ID, info);
        setSelfRetain(true);
        setExhaust(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new TensionStance()));
    }
}
