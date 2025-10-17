package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ScorchedSteel extends BaseCard {
    public static final String ID = makeID(ScorchedSteel.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CardRarity.UNCOMMON ,
            CardTarget.NONE,
            1
    );

    public ScorchedSteel() {
        super(ID, info);
        setMagic(1,0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK) {
                addToBot(new LoseHPAction(p, p, magicNumber));
                addToBot(new DrawCardAction(1));
            }
        }
        if (upgraded) {
            addToBot(new LoseHPAction(p, p, magicNumber));
            addToBot(new DrawCardAction(1));
        }
    }
}
