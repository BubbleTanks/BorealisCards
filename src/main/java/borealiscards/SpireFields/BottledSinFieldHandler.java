package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BottledSinFieldHandler {
@SpirePatch (clz = AbstractCard.class, method = SpirePatch.CLASS)
public static class BottledSinField {
    public static SpireField<Boolean> inBottleSin = new SpireField<>(() -> false);
}

@SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
public static class MakeStatEquivalentCopy {
    @SpirePostfixPatch
    public static AbstractCard plz(AbstractCard result, AbstractCard self) {
        BottledSinField.inBottleSin.set(result, BottledSinField.inBottleSin.get(self));
        return result;
    }
}
}