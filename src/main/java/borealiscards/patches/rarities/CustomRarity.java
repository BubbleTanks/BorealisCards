package borealiscards.patches.rarities;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CustomRarity {

    @SpireEnum
    public static AbstractCard.CardRarity SHOP;

    @SpireEnum
    public static AbstractCard.CardRarity EXOTIC;

    @SpirePatch2(clz = AbstractCard.class, method = "getPrice")
    public static class PricePatch {
        @SpirePrefixPatch
        public static SpireReturn<Integer> SetPrice(AbstractCard.CardRarity rarity) {
            if (rarity == CustomRarity.SHOP) {
                return SpireReturn.Return(100);
            }
            if (rarity == CustomRarity.EXOTIC) {
                return SpireReturn.Return(250);
            }
            return SpireReturn.Continue();
        }
    }



}
