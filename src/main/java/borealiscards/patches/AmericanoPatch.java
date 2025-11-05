package borealiscards.patches;

import borealiscards.relics.Americano;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmericanoPatch {

    @SpirePatch2(clz = AbstractCard.class, method = "freeToPlay")
    public static class BasicCostDown {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> coffee(AbstractCard __instance) {
            if (AbstractDungeon.player.hasRelic(Americano.ID)) {
                if (__instance.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || __instance.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                    return SpireReturn.Return(true);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
