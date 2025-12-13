package borealiscards.patches;

import borealiscards.cards.watcher.AmbrosialSpice;
import borealiscards.rewards.SpiceReward;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class SpicePatch {

    @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class SpiceRewardPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void replaceSpice(CombatRewardScreen __instance) {
            boolean has = false;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.cardID == AmbrosialSpice.ID) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                return;
            }
            ArrayList<RewardItem> rewardsToRemove = new ArrayList<>();
            for (RewardItem r : __instance.rewards) {
                if (r.type == RewardItem.RewardType.CARD) {
                    if (AbstractDungeon.cardRng.random(99) < AmbrosialSpice.spiceChance) {
                        rewardsToRemove.add(r);
                    }
                }
            }
            for (RewardItem r : rewardsToRemove) {
                __instance.rewards.remove(r);
                __instance.rewards.add(new SpiceReward());
            }
            AmbrosialSpice.spiceChance = 5;
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "show");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

    }
}
