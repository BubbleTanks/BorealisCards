package borealiscards.cards.silent;

import basemod.BaseMod;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SharpenedEdge extends BaseCard {
    public static final String ID = makeID(SharpenedEdge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    public SharpenedEdge() {
        super(ID, info);
        cardsToPreview = new Shiv();
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            cardsToPreview.upgrade();
            setExhaust(true);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(5));
        int handRemaining = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        for(int i = 0; i < handRemaining; ++i) {
            addToBot(new MakeTempCardInHandAction(cardsToPreview));
        }
    }
}
