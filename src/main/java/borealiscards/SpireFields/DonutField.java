package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DonutField {
    @SpirePatch(clz = AbstractCreature.class, method = SpirePatch.CLASS)
    public static class GainMaxHp {
        public static SpireField<Boolean> doNotHeal = new SpireField<>(() -> false);
    }
}
