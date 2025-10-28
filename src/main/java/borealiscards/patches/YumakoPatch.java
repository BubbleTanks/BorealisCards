package borealiscards.patches;

import borealiscards.relics.Yumako;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class YumakoPatch {

    @SpirePatch2(clz = ShowCardAndObtainEffect.class, method = "update")
    @SpirePatch2(clz = FastCardObtainEffect.class, method = "update")
    public static class YumakoReset {
        @SpireInsertPatch(locator = Locator.class, localvars = {"r"})
        public static void addCard(AbstractRelic r) {
            if (r.relicId == Yumako.ID) {
                if (!r.usedUp) {
                    r.counter = 3;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class, "onMasterDeckChange");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
