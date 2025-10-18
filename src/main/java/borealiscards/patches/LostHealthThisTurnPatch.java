package borealiscards.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

public class LostHealthThisTurnPatch {
    public static boolean hurtThisTurn = false;
    public static boolean hurtLastTurn = false;

    @SpirePatch(clz = AbstractPlayer.class, method = "updateCardsOnDamage")
    public static class LoseHPCheck {
        @SpirePrefixPatch()
        public static void updateCardsPatch (AbstractPlayer __instance) {
            hurtThisTurn = true;
        }
    }

    @SpirePatch2(clz = GameActionManager.class, method = "clear")
    public static class ResetCounters {
        @SpirePrefixPatch
        public static void reset() {
            hurtLastTurn = false;
            if(hurtThisTurn) hurtLastTurn = true;
            hurtThisTurn = false;
        }
    }

    @SpirePatch2(clz = GameActionManager.class, method = "getNextAction")
    public static class NewTurnCounters {
        @SpireInsertPatch(locator = Locator.class)
        public static void reset() {
            hurtLastTurn = false;
            if(hurtThisTurn) hurtLastTurn = true;
            hurtThisTurn = false;
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                return LineFinder.findInOrder(ctBehavior, new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics"));
            }
        }
    }
}
