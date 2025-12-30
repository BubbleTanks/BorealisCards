package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class FabricWings extends BaseRelic {
    public static final String ID = makeID(FabricWings.class.getSimpleName());

    public FabricWings() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.costForTurn == 0) {
            addToTop(new GainBlockAction(AbstractDungeon.player, 2));
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}