package borealiscards.patches;

import basemod.ReflectionHacks;
import borealiscards.relics.AntennaHeadband;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireRecallEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class AntennaPatch {

    @SpirePatch2(clz = CampfireUI.class, method = "update")
    public static class Antenna {
        @SpirePrefixPatch
        public static void leaveButton(CampfireUI __instance) {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                AbstractDungeon.overlayMenu.proceedButton.show();
            }
        }

        @SpireInsertPatch(locator = Locator.class)
        public static void resetButtons(CampfireUI __instance) {
            if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
                if (CampfireUI.hidden) {
                    AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
                        @Override
                        public void render(SpriteBatch spriteBatch) {
                        }

                        public void update() {
                            for(AbstractGameEffect abeff : AbstractDungeon.topLevelEffects) {
                                if (abeff instanceof ObtainKeyEffect) {
                                    Settings.hasRubyKey = true;
                                    break;
                                }
                            }

                            for(AbstractGameEffect abeff : AbstractDungeon.effectList) {
                                if (abeff instanceof CampfireRecallEffect) {
                                    Settings.hasRubyKey = true;
                                    break;
                                }
                            }

                            RestRoom room = ((RestRoom) AbstractDungeon.getCurrRoom());

                            ArrayList<AbstractCampfireOption> buttons = ReflectionHacks.getPrivate(room.campfireUI, CampfireUI.class, "buttons");
                            buttons.clear();
                            ReflectionHacks.privateMethod(CampfireUI.class, "initializeButtons").invoke(room.campfireUI);
                            room.campfireUI.reopen();
                            room.cutFireSound();
                            this.isDone = true;
                        }

                        @Override
                        public void dispose() {
                        }
                    });
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

}
