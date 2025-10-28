package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DynamoEngine extends BaseCard {
    public static final String ID = makeID(DynamoEngine.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.NONE,
            0
    );

    public DynamoEngine() {
        super(ID, info);
        setExhaust(true,false);
    }

    private int DynamoCards() {
        int i = 0;
        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.POWER) {
                i++;
            }
        }
        return i;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(DynamoCards()));
    }
}
