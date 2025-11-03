package borealiscards.patches;

import borealiscards.SpireFields.DonutField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class IncreaseMaxHpPatch {

    @SpirePatch2(clz = AbstractCreature.class, method = "increaseMaxHp")
    public static class ButDontHeal {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> hpPatch(AbstractCreature __instance) {
            if (DonutField.GainMaxHp.doNotHeal.get(__instance)) {
                __instance.healthBarUpdatedEvent();
                DonutField.GainMaxHp.doNotHeal.set(__instance, false);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }


        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "heal");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


}
