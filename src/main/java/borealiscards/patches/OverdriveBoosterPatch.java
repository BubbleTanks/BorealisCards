package borealiscards.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import javassist.CtBehavior;

public class OverdriveBoosterPatch
{

    public static int OVERDRIVEORBSLOT = 10;

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="increaseMaxOrbSlots"
    )
    public static class IncreaseMaxOrbSlots
    {
        public static SpireReturn<Void> Prefix(AbstractPlayer __instance, @ByRef int[] amount, boolean playSfx)
        {
            if (__instance.maxOrbs == 10) {
                if (OVERDRIVEORBSLOT != 10) {
                    __instance.maxOrbs = 11;
                    amount[0] -= 1;
                    // To make up for the one we're skipping from the amount
                    __instance.orbs.add(new EmptyOrbSlot());
                }
            }
            if (__instance.maxOrbs == OVERDRIVEORBSLOT) {
                AbstractDungeon.effectList.add(new ThoughtBubble(__instance.dialogX, __instance.dialogY, 3.0f, AbstractPlayer.MSG[3], true));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = GameActionManager.class, method = "clear")
    public static class ResetCounters {
        @SpirePrefixPatch
        public static void reset() {
            OVERDRIVEORBSLOT = 10;
        }
    }

    @SpirePatch(
            clz= AbstractOrb.class,
            method="setSlot"
    )
    public static class SetSlotPosition
    {
        @SpireInsertPatch(
                rloc=1,
                localvars={"dist"}
        )
        public static void ChangeDist(AbstractOrb __instance, int slotNum, int maxOrbs, @ByRef float[] dist)
        {
            if (maxOrbs > 10) {
                dist[0] = 160.0f * Settings.scale + 10 * 10.0f * Settings.scale;
            }
        }

        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tY"}
        )
        public static void ChangeY(AbstractOrb __instance, int slotNum, int maxOrbs, @ByRef float[] tY)
        {
            if (maxOrbs > 10) {
                tY[0] += 100.0f * Settings.scale * ((float)(maxOrbs - 10) / ((float)OVERDRIVEORBSLOT - 10));
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "move");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
