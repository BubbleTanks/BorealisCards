package borealiscards.cardmods;

import basemod.abstracts.AbstractCardModifier;
import borealiscards.BorealisCards;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MarkingMod extends AbstractCardModifier {
    public static String ID = BorealisCards.makeID(MarkingMod.class.getSimpleName());

    @Override
    public AbstractCardModifier makeCopy() {
        return new MarkingMod();
    }

    public String identifier (AbstractCard c) {
        return ID;
    }
}
