package borealiscards.patches;

import borealiscards.relics.Ankh;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class AnkhPatch {

    @SpirePatch2(clz = ApplyPowerAction.class, method = "update")
    public static class PowerInterrupt {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> killYourself(ApplyPowerAction __instance, AbstractPower ___powerToApply, @ByRef float[] ___duration) {

            if (AbstractDungeon.player.hasRelic(Ankh.ID) && __instance.target.isPlayer && AbstractDungeon.player.stance.ID == NeutralStance.STANCE_ID && ___powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r.relicId == Ankh.ID) {
                        r.flash();
                        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, r));
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(__instance.target, ApplyPowerAction.TEXT[0]));
                        ___duration[0] -= Gdx.graphics.getDeltaTime();
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                    }
                }
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "isPlayer");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

    }
}
