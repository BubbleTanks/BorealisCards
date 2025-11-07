package borealiscards.patches;

import borealiscards.powers.EdgeAlignmentPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class AlignmentPatch {


    @SpirePatch2(clz = DiscardSpecificCardAction.class, method = "update")
    public static class OnDiscardSpecificShiv {
        @SpireInsertPatch(locator = Locator.class)
        public static void gainAccuracy(AbstractCard ___targetCard) {
            if (___targetCard.cardID == Shiv.ID) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p.ID == EdgeAlignmentPower.POWER_ID) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(___targetCard, AbstractDungeon.player.discardPile));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AccuracyPower(AbstractDungeon.player, p.amount)));
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnManualDiscard");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = DiscardAction.class, method = "update")
    public static class OnDiscardShiv {
        @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
        public static void gainAccuracy(AbstractCard c) {
            if (c.cardID == Shiv.ID) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p.ID == EdgeAlignmentPower.POWER_ID) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AccuracyPower(AbstractDungeon.player, p.amount)));
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnManualDiscard");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
