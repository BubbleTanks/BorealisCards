package borealiscards.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import javassist.CtBehavior;

@SpirePatch2(clz = ChangeStanceAction.class, method = "update")
public class PreviousStancePatch {

    public static AbstractStance previousStance = new NeutralStance();

    @SpireInsertPatch(locator = Locator.class, localvars = {"oldStance"})
    public static void PreviousStance(AbstractStance oldStance) {
        previousStance = oldStance;
    }

    @SpirePatch2(clz = GameActionManager.class, method = "clear")
    public static class ResetCounters {
        @SpirePrefixPatch
        public static void reset() {
            previousStance = new NeutralStance();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "switchedStance");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
