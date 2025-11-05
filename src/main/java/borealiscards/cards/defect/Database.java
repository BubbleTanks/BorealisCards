package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Database extends BaseCard {
    public static final String ID = makeID(Database.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public Database() {
        super(ID, info);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = magicNumber; i > 0; i--) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
            addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
        }

    }
}

// wawa :3
