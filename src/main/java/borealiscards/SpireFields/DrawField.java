package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class DrawField {
    @SpirePatch(clz = DrawCardAction.class, method = SpirePatch.CLASS)
    public static class HazardDraw {
        public static SpireField<Boolean> drawCardsFast = new SpireField<>(() -> false);
    }
}
