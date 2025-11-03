package borealiscards.patches;

import basemod.BaseMod;
import borealiscards.SpireFields.DrawField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class DrawCardsReallyFastPatch {

    @SpirePatch2(clz = DrawCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, int.class, boolean.class})
    public static class DrawThemRightNow {
        @SpirePostfixPatch
        public static void hazardDraw(DrawCardAction __instance, @ByRef float[] ___duration) {
            if (DrawField.HazardDraw.drawCardsFast.get(__instance)) {
                ___duration[0] = 0.01F;
            }
        }
    }

    @SpirePatch2(clz = DrawCardAction.class, method = "update")
    public static class DrawThemAgain {
        @SpireInsertPatch(locator = Locator1.class)
        public static void horrifyingLocation1(DrawCardAction __instance, @ByRef float[] ___duration) {
            if (DrawField.HazardDraw.drawCardsFast.get(__instance)) {
                ___duration[0] = 0.01F;
            }
        }

        @SpireInsertPatch(locator = Locator2.class, localvars = {"deckSize"})
        public static SpireReturn<Void> horrifyingLocation2(DrawCardAction __instance, AbstractGameAction ___followUpAction, @ByRef int[] deckSize) {
            if (DrawField.HazardDraw.drawCardsFast.get(__instance) && __instance.amount + AbstractDungeon.player.hand.size() <= BaseMod.MAX_HAND_SIZE && __instance.amount > deckSize[0]) {
                int tmp = __instance.amount - deckSize[0];
                AbstractGameAction drawCards = new DrawCardAction(tmp, ___followUpAction, false);
                DrawField.HazardDraw.drawCardsFast.set(drawCards, true);

                AbstractDungeon.actionManager.addToTop(drawCards);
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                if (deckSize[0] != 0) {
                    AbstractGameAction drawCards2 = new DrawCardAction(deckSize[0], false);
                    DrawField.HazardDraw.drawCardsFast.set(drawCards2, true);

                    AbstractDungeon.actionManager.addToTop(drawCards2);
                }

                __instance.amount = 0;
                __instance.isDone = true;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "ACTION_DUR_FASTER");
                int[] var = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                var[0] += 2;
                return var;
            }
        }

        private static class Locator2 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(DrawCardAction.class, "shuffleCheck");
                int[] var = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                var[0] += 1;
                return var;
            }
        }
    }
}
