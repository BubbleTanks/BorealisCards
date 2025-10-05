package borealiscards.patches.adventuringpaintpatches;

import borealiscards.relics.AdventuringPaint;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "preBattlePrep"
)
public class AdventuringPaintPreBattlePatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractPlayer __instance) {
        if (__instance.hasRelic(AdventuringPaint.ID)) {
            ((AdventuringPaint)__instance.getRelic(AdventuringPaint.ID)).beforeEnergyPrep();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(EnergyManager.class, "prep");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}