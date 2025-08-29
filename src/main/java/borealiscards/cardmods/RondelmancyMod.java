package borealiscards.cardmods;

import basemod.abstracts.AbstractCardModifier;
import borealiscards.BorealisCards;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RondelmancyMod extends AbstractCardModifier {
    public static String ID = BorealisCards.makeID(RondelmancyMod.class.getSimpleName());

    @Override
    public AbstractCardModifier makeCopy() {
        return new RondelmancyMod();
    }

    public String identifier (AbstractCard c) {
        return ID;
    }
}
