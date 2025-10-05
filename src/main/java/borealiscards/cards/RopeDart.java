package borealiscards.cards;

import basemod.BaseMod;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RopeDart extends BaseCard {
    public static final String ID = makeID(RopeDart.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public RopeDart() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
}
