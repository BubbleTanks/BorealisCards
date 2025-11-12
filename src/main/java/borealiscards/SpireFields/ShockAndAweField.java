package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class ShockAndAweField {
    @SpirePatch(clz = AbstractOrb.class, method = SpirePatch.CLASS)
    public static class ShockField {
        public static SpireField<Boolean> aweShocked = new SpireField<>(() -> false);
        public static SpireField<Boolean> sentient = new SpireField<>(() -> false);
    }
}
