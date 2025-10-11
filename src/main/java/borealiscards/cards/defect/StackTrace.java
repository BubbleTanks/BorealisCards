package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StackTrace extends BaseCard {
    public static final String ID = makeID(StackTrace.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public StackTrace() {
        super(ID, info);
        setMagic(1);
        setExhaust(true);
        cardsToPreview = new VoidCard();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseMaxOrbAction(magicNumber));
        if(!upgraded) {
            addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 1));
        } else {
            addToBot(new MakeTempCardInDiscardAction(new Dazed(), 1));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            cardsToPreview = new Dazed();
        }
    }
}
