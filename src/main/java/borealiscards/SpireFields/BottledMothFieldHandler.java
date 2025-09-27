package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BottledMothFieldHandler {
@SpirePatch (clz = AbstractCard.class, method = SpirePatch.CLASS)
public static class BottledMothField {
    public static SpireField<Boolean> inBottleMoth = new SpireField<>(() -> false);
}

@SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
public static class MakeStatEquivalentCopy {
    @SpirePostfixPatch
    public static AbstractCard plz(AbstractCard result, AbstractCard self) {
        BottledMothField.inBottleMoth.set(result, BottledMothField.inBottleMoth.get(self));
        return result;
    }
}
}