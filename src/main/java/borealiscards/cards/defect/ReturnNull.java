package borealiscards.cards.defect;

import borealiscards.actions.DrawPileToHandAction2;
import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReturnNull extends BaseCard {
    public static final String ID = makeID(ReturnNull.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            0
    );

    public ReturnNull() {
        super(ID, info);
        cardsToPreview = new VoidCard();
        setMagic(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            addToBot(new MakeTempCardInDrawPileAction(cardsToPreview, 1, true, false));
        } else {
            addToBot(new MakeTempCardInDiscardAction(cardsToPreview, 1));
        }

        addToBot(new DrawPileToHandAction2(magicNumber, 0));

    }
}
