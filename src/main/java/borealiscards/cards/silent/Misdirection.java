package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Misdirection extends BaseCard {
    public static final String ID = makeID(Misdirection.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            0
    );

    public Misdirection() {
        super(ID, info);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsAction(p.drawPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], true, card -> true, (cards) -> {
            for (AbstractCard c : cards) {
                p.drawPile.removeCard(c);
                p.hand.addToHand(c);
                addToBot(new DiscardSpecificCardAction(c));
            }
        }));
    }
}
