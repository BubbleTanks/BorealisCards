package borealiscards.cards.watcher;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import borealiscards.actions.SpiritWalkAction;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpiritWalk extends BaseCard {
    public static final String ID = makeID(SpiritWalk.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public SpiritWalk() {
        super(ID, info);
        MultiCardPreview.add(this, new Smite(), new Safety());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpiritWalkAction());
    }
}
