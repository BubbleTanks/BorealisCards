package borealiscards.patches;

import borealiscards.BorealisCards;
import borealiscards.relics.AntennaHeadband;
import borealiscards.vfx.AntennaGameEffect;
import com.badlogic.gdx.Graphics;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class AntennaPatch {

    private static final UIStrings fuckThisShovel = CardCrawlGame.languagePack.getUIString(BorealisCards.makeID("IHateThisFuckingShovel"));

    @SpirePatch2(clz = CampfireUI.class, method = "update")
    public static class Antenna {
        @SpirePrefixPatch
        public static void leaveButton(CampfireUI __instance) {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                AbstractDungeon.overlayMenu.proceedButton.show();
                AbstractDungeon.overlayMenu.proceedButton.setLabel(fuckThisShovel.TEXT[2]);
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
        }

        @SpireInsertPatch(locator = Locator.class)
        public static void resetButtons(CampfireUI __instance) {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                boolean antennaHere = false;
                for (AbstractGameEffect abeff : AbstractDungeon.effectList) {
                    if (abeff instanceof AntennaGameEffect) {
                        antennaHere = true;
                        break;
                    }
                }
                if (CampfireUI.hidden && !antennaHere) {
                    AbstractDungeon.effectList.add(new AntennaGameEffect());
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Graphics.class, "getDeltaTime");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = CancelButton.class, method = "update")
    public static class WhyTheFuckWePatchingTheCancelButton {
        @SpireInsertPatch(locator = Locator.class)
        public static void cancelButtonPatch() {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                    AbstractDungeon.closeCurrentScreen();
                    if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                        ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(GridCardSelectScreen.class, "forUpgrade");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class CombatReopen {
        @SpireInsertPatch(locator = Locator.class)
        public static void makeUpYourGODDAMNMIND() {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    AbstractDungeon.overlayMenu.cancelButton.showInstantly(fuckThisShovel.TEXT[1]);
                    AbstractDungeon.overlayMenu.proceedButton.hide();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "show");
                int[] var = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                var[0] += 1;
                return var;
            }
        }
    }


    @SpirePatch2(clz = CombatRewardScreen.class, method = "reopen")
    public static class CombatFrustrating {
        @SpirePostfixPatch
        public static void makeUpYourGODDAMNMIND() {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    AbstractDungeon.overlayMenu.cancelButton.showInstantly(fuckThisShovel.TEXT[1]);
                    AbstractDungeon.overlayMenu.proceedButton.hide();
                }
            }
        }
    }

    @SpirePatch2(clz = CombatRewardScreen.class, method = "setLabel")
    public static class Words {
        @SpirePostfixPatch
        public static void thankGodNoLocators(CombatRewardScreen __instance) {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    if (__instance.rewards.isEmpty()) {
                        AbstractDungeon.overlayMenu.cancelButton.buttonText = fuckThisShovel.TEXT[0];
                    } else {
                        AbstractDungeon.overlayMenu.cancelButton.buttonText = fuckThisShovel.TEXT[1];
                    }
                }
            }
        }
    }

}
