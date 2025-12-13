package borealiscards.patches;

import borealiscards.cards.watcher.Firewatch;
import borealiscards.powers.CruelSunPower;
import borealiscards.powers.IncensedPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch2(
        clz = ChangeStanceAction.class, method = "update"
)

public class StanceInterruptPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(ChangeStanceAction __instance) {
        if(AbstractDungeon.player.hasPower(CruelSunPower.POWER_ID) || AbstractDungeon.player.hasPower(IncensedPower.POWER_ID)) {
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p.ID == CruelSunPower.POWER_ID || p.ID == IncensedPower.POWER_ID) p.flash();
            }
            __instance.isDone = true;
            return SpireReturn.Return();
        }

        return SpireReturn.Continue();
    }

    @SpireInsertPatch(locator = Locator.class)
    public static void firewatch() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID == Firewatch.ID) {
                ((Firewatch)c).vigorUp();
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractStance.class, "ID");
            int[] var = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            var[0] += 1;
            return var;
        }
    }
}
