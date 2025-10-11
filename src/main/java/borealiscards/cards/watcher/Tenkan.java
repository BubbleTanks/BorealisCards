package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.PreviousStancePatch;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Tenkan extends BaseCard {
    public static final String ID = makeID(Tenkan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public Tenkan() {
        super(ID, info);
        setSelfRetain(true);
        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(PreviousStancePatch.previousStance));
    }
}
