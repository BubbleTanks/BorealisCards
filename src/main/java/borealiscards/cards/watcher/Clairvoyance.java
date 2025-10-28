package borealiscards.cards.watcher;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import borealiscards.cards.BaseCard;
import borealiscards.stances.TensionStance;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Clairvoyance extends BaseCard {
    public static final String ID = makeID(Clairvoyance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Clairvoyance() {
        super(ID, info);
        MultiCardPreview.add(this, new Miracle(), new Insight());
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
        if (AbstractDungeon.player.stance.ID.equals(TensionStance.STANCE_ID)) {
            for(AbstractCard c : MultiCardPreview.multiCardPreview.get(this)) {
                addToBot(new MakeTempCardInHandAction(c, 1));
            }
        } else addToBot(new ChangeStanceAction(new TensionStance()));
    }
}
