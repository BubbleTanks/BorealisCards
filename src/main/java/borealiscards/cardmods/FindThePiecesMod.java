package borealiscards.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import borealiscards.BorealisCards;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FindThePiecesMod extends AbstractCardModifier {
    public static String ID = BorealisCards.makeID(FindThePiecesMod.class.getSimpleName());

    @Override
    public AbstractCardModifier makeCopy() {
        return new FindThePiecesMod ();
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card,ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}