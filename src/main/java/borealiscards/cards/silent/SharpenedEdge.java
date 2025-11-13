package borealiscards.cards.silent;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class SharpenedEdge extends BaseCard implements CustomSavable<Boolean> {
    public static final String ID = makeID(SharpenedEdge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    public static boolean hasWarned = false;

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
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int handRemaining = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
                if (handRemaining > 10){
                    handRemaining = 10;
                    if (!hasWarned) {
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, cardStrings.EXTENDED_DESCRIPTION[0], true));
                        hasWarned = true;
                    }
                }

                for (int i = 0; i < handRemaining; ++i) {
                    addToBot(new MakeTempCardInHandAction(cardsToPreview));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public Boolean onSave() {
        return hasWarned;
    }

    @Override
    public void onLoad(Boolean b) {
        hasWarned = b;
    }
}
