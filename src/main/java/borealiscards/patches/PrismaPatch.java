package borealiscards.patches;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.PrismaMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderEnergy",
        paramtypez = {SpriteBatch.class}
)
public class PrismaPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static SpireReturn<Void> Insert(AbstractCard __instance, SpriteBatch sb) {
        if (CardModifierManager.hasModifier(__instance, PrismaMod.ID))
            return SpireReturn.Return();
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(Color.class, "cpy");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
