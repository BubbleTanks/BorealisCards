package borealiscards.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import borealiscards.BorealisCards;
import borealiscards.actions.BurningMontanteCardModAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class BurningMontanteMod extends AbstractCardModifier {
    public static String ID = BorealisCards.makeID(BurningMontanteMod.class.getSimpleName());

    @Override
    public AbstractCardModifier makeCopy() {
        return new BurningMontanteMod();
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card,ID);
    }

    public void onInitialApplication(AbstractCard card) {
        card.modifyCostForCombat(+1);
    }

    public void onRemove(AbstractCard card) {
        card.modifyCostForCombat(-1);
    }

    public void onApplyPowers(AbstractCard card) {
        if (card.cost < 2) {
            addToBot(new BurningMontanteCardModAction(card));
        }
    }

    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (card.cost < 2) {
            addToBot(new BurningMontanteCardModAction(card));
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}