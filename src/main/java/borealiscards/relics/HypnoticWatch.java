package borealiscards.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

@AutoAdd.Ignore
public class HypnoticWatch extends BaseRelic {
    public static final String ID = makeID(HypnoticWatch.class.getSimpleName());
    private boolean triggeredThisTurn = false;
    public HypnoticWatch() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    public void atTurnStartPostDraw() {
        flash();
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(),1,true,true));
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(drawnCard.type == AbstractCard.CardType.STATUS && !triggeredThisTurn) {
            triggeredThisTurn = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            flash();
            addToBot(new ExhaustSpecificCardAction(drawnCard, AbstractDungeon.player.hand));
            addToBot(new DrawCardAction(1));
            addToBot(new GainEnergyAction(1));
        }
    }
}
