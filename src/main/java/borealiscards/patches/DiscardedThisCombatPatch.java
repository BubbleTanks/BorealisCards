package borealiscards.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;

public class DiscardedThisCombatPatch {
    public static int cardsDiscardedThisCombat = 0;

    @SpirePatch(clz = GameActionManager.class, method = "incrementDiscard")
    public static class DiscardTotal {
        @SpirePrefixPatch
        public static void DiscardTracker() {
            cardsDiscardedThisCombat++;
        }
    }

    @SpirePatch2(clz = GameActionManager.class, method = "clear")
    public static class ResetCounters {
        @SpirePrefixPatch
        public static void reset() {
            cardsDiscardedThisCombat = 0;
        }
    }
}
