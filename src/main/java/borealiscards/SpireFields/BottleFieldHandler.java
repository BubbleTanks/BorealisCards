package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BottleFieldHandler {
@SpirePatch (clz = AbstractCard.class, method = SpirePatch.CLASS)
public static class BottleField {
    public static SpireField<Boolean> inBottleMoth = new SpireField<>(() -> false);
    public static SpireField<Boolean> inBottleSin = new SpireField<>(() -> false);
    public static SpireField<Boolean> inBlackIndigonium = new SpireField<>(() -> false);
    public static SpireField<Boolean> playedByIndigonium = new SpireField<>(() -> false);
}

@SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
public static class MakeStatEquivalentCopy {
    @SpirePostfixPatch
    public static AbstractCard plz(AbstractCard result, AbstractCard self) {
        BottleField.inBottleMoth.set(result, BottleField.inBottleMoth.get(self));
        BottleField.inBottleSin.set(result, BottleField.inBottleSin.get(self));
        BottleField.inBlackIndigonium.set(result, BottleField.inBlackIndigonium.get(self));
        return result;
    }
}
}