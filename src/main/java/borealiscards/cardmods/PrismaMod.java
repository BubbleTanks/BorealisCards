package borealiscards.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import borealiscards.BorealisCards;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class PrismaMod extends AbstractCardModifier {
    public static String ID = BorealisCards.makeID(PrismaMod.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("borealiscards:PrismaMod");

    @Override
    public AbstractCardModifier makeCopy() {
        return new PrismaMod();
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card,ID) || card.cost == -2;
    }

    public boolean canPlayCard(AbstractCard card) {
        card.cantUseMessage = uiStrings.TEXT[1];
        return false;
    }

    public String modifyDescription(String raw, AbstractCard card) {
        return uiStrings.TEXT[0] + raw;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}