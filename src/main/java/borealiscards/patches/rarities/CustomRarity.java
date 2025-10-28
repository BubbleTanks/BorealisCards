package borealiscards.patches.rarities;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.runHistory.TinyCard;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static borealiscards.BorealisCards.makeID;

public class CustomRarity {

    @SpireEnum
    public static AbstractCard.CardRarity SHOP;

    @SpireEnum
    public static AbstractCard.CardRarity EXOTIC;

    @SpireEnum
    public static AbstractCard.CardRarity RAREDUMMY;

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

    @SpirePatch2(clz = CardGroup.class, method = "sortByRarityPlusStatusCardType")
    @SpirePatch2(clz = CardGroup.class, method = "sortByRarity")
    public static class ManualSort {

        private static List<AbstractCard.CardRarity> customOrder = Arrays.asList(AbstractCard.CardRarity.BASIC, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardRarity.COMMON, AbstractCard.CardRarity.UNCOMMON, CustomRarity.SHOP, AbstractCard.CardRarity.RARE, CustomRarity.EXOTIC, AbstractCard.CardRarity.CURSE);


        @SpirePostfixPatch
        public static void InjectRarity(CardGroup __instance, boolean ascending) {
            ReflectionHacks.privateMethod(CardGroup.class, "sortWithComparator", Comparator.class, boolean.class).invoke(__instance, new CustomRarityComparator(), ascending);
            ReflectionHacks.privateMethod(CardGroup.class, "sortWithComparator", Comparator.class, boolean.class).invoke(__instance, new StatusCardsLastComparator(), ascending);
        }

        private static class StatusCardsLastComparator implements Comparator<AbstractCard> {
            private StatusCardsLastComparator() {}

            public int compare(AbstractCard c1, AbstractCard c2) {
                if (c1.type == AbstractCard.CardType.STATUS && c2.type != AbstractCard.CardType.STATUS)
                    return 1;
                if (c1.type != AbstractCard.CardType.STATUS && c2.type == AbstractCard.CardType.STATUS)
                    return -1;
                return 0;
            }
        }

        private static class CustomRarityComparator implements Comparator<AbstractCard> {
            private CustomRarityComparator() {}

            public int compare(AbstractCard c1, AbstractCard c2) {
                return Integer.compare(customOrder.indexOf(c1.rarity), customOrder.indexOf(c2.rarity));
            }
        }
    }

    @SpirePatch2(clz = TinyCard.class, method = "getIconBannerColor")
    public static class TinyCustomCards {
        @SpirePrefixPatch
        public static SpireReturn<Color> TinyColor(AbstractCard card) {
            if (card.rarity == CustomRarity.SHOP) {
                return SpireReturn.Return(CardBorderPatch.BANNER_COLOR_SHOP);
            }
            if (card.rarity == CustomRarity.EXOTIC) {
                return SpireReturn.Return(CardBorderPatch.BANNER_COLOR_EXOTIC);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = RunHistoryScreen.class, method = "rarityLabel", paramtypez = {AbstractCard.CardRarity.class})
    public static class Labels {

        public static UIStrings RarityStrings = CardCrawlGame.languagePack.getUIString(makeID("CardRarityStrings"));

        @SpirePrefixPatch
        public static SpireReturn<String> CardLabel(AbstractCard.CardRarity rarity) {

            if (rarity == CustomRarity.SHOP) {
                return SpireReturn.Return(RarityStrings.TEXT[0]);
            }

            if (rarity == CustomRarity.EXOTIC) {
                return SpireReturn.Return(RarityStrings.TEXT[1]);
            }

            return SpireReturn.Continue();
        }
    }

}
