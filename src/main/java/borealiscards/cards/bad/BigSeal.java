package borealiscards.cards.bad;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class BigSeal extends BaseCard {
    public static final String ID = makeID(BigSeal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF_AND_ENEMY,
            1
    );

    public BigSeal() {
        super(ID, info);
        setCostUpgrade(2);
        AbstractCard card2 = new BigSeal(true);
        if (upgraded) card2.upgrade();
        MultiCardPreview.add(this, card2, card2, card2, card2, card2, card2);
    }

    private BigSeal(boolean isConstructor2) {
        super(ID, info);
        setCostUpgrade(2);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            for(AbstractCard c : MultiCardPreview.multiCardPreview.get(this)) {
                c.upgrade();
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard card = new BigSeal();
                if (BigSeal.this.upgraded) card.upgrade();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
                this.isDone = true;
            }
        });
    }
}


