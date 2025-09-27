package borealiscards.actions;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.BurningMontanteMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BurningMontanteCardModAction extends AbstractGameAction {
    private final AbstractCard card;

    public BurningMontanteCardModAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        CardModifierManager.removeModifiersById(card, BurningMontanteMod.ID, true);
        this.isDone = true;
    }
}
