package borealiscards.patches.rarities;

import borealiscards.BorealisCards;
import borealiscards.ui.ModConfig;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.util.ArrayList;
import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

// NOT the poolrooms
public class PoolPatch {

    public static CardGroup shopCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup srcShopCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public static CardGroup exoticCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup srcExoticCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    @SpirePatch2(clz = Merchant.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, int.class})
    public static class MerchantPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void MerchantInitialCards(ArrayList<AbstractCard> ___cards2) {
            if(ModConfig.ShopCards && !srcShopCardPool.isEmpty()) {
                if (AbstractDungeon.cardRng.randomBoolean()) {
                    ___cards2.remove(___cards2.get(0));
                    ___cards2.add(0, srcShopCardPool.getRandomCard(true).makeCopy());
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "id");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = ShopScreen.class, method = "purchaseCard")
    public static class StupidFuckingRatPatch {
        @SpireInsertPatch(locator = Locator2.class, localvars = {"c", "tempRarity"})
        public static void MerchantPurchasedColorlessCards(AbstractCard.CardRarity tempRarity, @ByRef AbstractCard[] c) {
            if(ModConfig.ShopCards && tempRarity == AbstractCard.CardRarity.UNCOMMON && !srcShopCardPool.isEmpty()) {
                if (AbstractDungeon.cardRng.randomBoolean()) {
                    c[0] = srcShopCardPool.getRandomCard(true).makeCopy();
                } else {
                    c[0] = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON).makeCopy();
                }
            }
        }

        private static class Locator2 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }


        @SpireInstrumentPatch
        public static ExprEditor MummifiedHandCheck() {
            return new ExprEditor() {
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(AbstractCard.class.getName()) && f.getFieldName().equals("color")) {
                        f.replace("if (" + StupidFuckingRatPatch.class.getName() + ".Do(hoveredCard)) {" +
                                "$_ = " + AbstractCard.CardColor.class.getName() + ".COLORLESS;" +
                                "} else {" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }

        @SuppressWarnings("unused")
        public static boolean Do(AbstractCard ratDo) {
            if (ratDo.rarity == CustomRarity.SHOP) {
                return true;
            }

            return false;
        }
    }

    private static boolean ExoticChance() {
        int roll = AbstractDungeon.cardRng.random(74);
        if(roll == 1) return true;
        return false;
    }

    private static AbstractCard.CardRarity ExoticRarity(AbstractCard.CardRarity rarity) {
        if(ModConfig.ExoticCards && rarity == AbstractCard.CardRarity.RARE) {
            if(ExoticChance()) {
                return CustomRarity.EXOTIC;
            }
        }
        return rarity;
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
    public static class ExoticAntiBreak {
        @SpireInsertPatch(locator = Locator.class, localvars = {"retVal", "rarity"})
        public static void ExoticFallback(ArrayList<AbstractCard> retVal, @ByRef AbstractCard.CardRarity[] rarity) {
            if(!exoticCardPool.isEmpty()) {
                int i = 0;
                for (AbstractCard c : retVal) {
                    if (c.rarity == CustomRarity.EXOTIC) {
                        i++;
                    }
                }
                if (rarity[0] == CustomRarity.EXOTIC && i >= exoticCardPool.size()) {
                    rarity[0] = CustomRarity.RAREDUMMY;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getCard", paramtypez = {AbstractCard.CardRarity.class})
    public static class RarityWasRolled {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> ReturnRarity(AbstractCard.CardRarity rarity) {
            rarity = ExoticRarity(rarity);
            if(rarity == CustomRarity.EXOTIC) {
                if (ModConfig.ExoticCards && !exoticCardPool.isEmpty()) {
                    return SpireReturn.Return(exoticCardPool.getRandomCard(true));
                } else {
                    BorealisCards.logger.warn("Warning! No Exotic cards found!");
                    return SpireReturn.Return(AbstractDungeon.rareCardPool.getRandomCard(true));
                }
            }
            if(rarity == CustomRarity.RAREDUMMY) {
                return SpireReturn.Return(AbstractDungeon.rareCardPool.getRandomCard(true));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getCard", paramtypez = {AbstractCard.CardRarity.class, Random.class})
    public static class RarityWasRolledWithRNG {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> ReturnRarity(AbstractCard.CardRarity rarity, Random rng) {
            rarity = ExoticRarity(rarity);
            if(rarity == CustomRarity.EXOTIC) {
                if (ModConfig.ExoticCards && !exoticCardPool.isEmpty()) {
                    return SpireReturn.Return(exoticCardPool.getRandomCard(rng));
                } else {
                    BorealisCards.logger.warn("Warning! No Exotic cards found!");
                    return SpireReturn.Return(AbstractDungeon.rareCardPool.getRandomCard(rng));
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getCardWithoutRng")
    public static class RarityWasRolledWithoutRNG {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> ReturnRarity(AbstractCard.CardRarity rarity) {
            rarity = ExoticRarity(rarity);
            if(rarity == CustomRarity.EXOTIC) {
                if (ModConfig.ExoticCards && !exoticCardPool.isEmpty()) {
                    return SpireReturn.Return(exoticCardPool.getRandomCard(false));
                } else {
                    BorealisCards.logger.warn("Warning! No Exotic cards found!");
                    return SpireReturn.Return(AbstractDungeon.rareCardPool.getRandomCard(false));
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getCardFromPool")
    public static class RarityWasRolledFromPool {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> ReturnRarity(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng) {
            rarity = ExoticRarity(rarity);
            if(rarity == CustomRarity.EXOTIC) {
                if (ModConfig.ExoticCards && !exoticCardPool.getCardsOfType(type).isEmpty()) {
                    return SpireReturn.Return(exoticCardPool.getRandomCard(type, useRng));
                } else {
                    BorealisCards.logger.warn("Warning! No Exotic cards found!");
                    return SpireReturn.Return(AbstractDungeon.rareCardPool.getRandomCard(type, useRng));
                }
            }
            return SpireReturn.Continue();
        }
    }

    // WHY DO I NEED TO DO THIS FOUR FUCKING TIMES

    @SpirePatch2(clz = AbstractDungeon.class, method = "returnTrulyRandomCard")
    public static class FalselyRandom {

        @SpireInsertPatch(rloc = 2, localvars = {"list"})
        public static void ShopInsert(ArrayList<AbstractCard> list) {
            if (ModConfig.ShopCards && !srcShopCardPool.isEmpty()) {
                list.addAll(srcShopCardPool.group);
            }
        }

        @SpirePostfixPatch
        public static AbstractCard ExoticOverride(AbstractCard __result) {
            if(ModConfig.ExoticCards && __result.rarity == AbstractCard.CardRarity.RARE && !srcExoticCardPool.isEmpty()) {
                if (ExoticChance()) {
                    return srcExoticCardPool.getRandomCard(true);
                }
            }
            return __result;
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "returnTrulyRandomCardInCombat", paramtypez = {})
    public static class FalselyRandomInCombat {

        @SpireInsertPatch(rloc = 1, localvars = {"list"})
        public static void ShopInsert(ArrayList<AbstractCard> list) {
            if (ModConfig.ShopCards && !srcShopCardPool.isEmpty()) {
                for (AbstractCard c : srcShopCardPool.group) {
                    if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                        list.add(c);
                    }
                }
            }
        }

        @SpirePostfixPatch
        public static AbstractCard ExoticOverride(AbstractCard __result) {
            if(ModConfig.ExoticCards && __result.rarity == AbstractCard.CardRarity.RARE && !srcExoticCardPool.isEmpty()) {
                if (ExoticChance()) {
                    ArrayList<AbstractCard> ExoticNotHealing = new ArrayList<>();
                    for (AbstractCard c : srcExoticCardPool.group) {
                        if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                            ExoticNotHealing.add(c);
                        }
                    }
                    if (ExoticNotHealing.isEmpty()) return __result;
                    return ExoticNotHealing.get(cardRandomRng.random(ExoticNotHealing.size() - 1));
                }
            }
            return __result;
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "returnTrulyRandomCardInCombat", paramtypez = {AbstractCard.CardType.class})
    public static class FalselyRandomInCombat2 {

        @SpireInsertPatch(rloc = 1, localvars = {"list"})
        public static void ShopInsert(AbstractCard.CardType type, ArrayList<AbstractCard> list) {
            if (ModConfig.ShopCards && !srcShopCardPool.isEmpty()) {
                for (AbstractCard c : srcShopCardPool.group) {
                    if (!c.hasTag(AbstractCard.CardTags.HEALING) && c.type == type) {
                        list.add(c);
                    }
                }
            }
        }

        @SpirePostfixPatch
        public static AbstractCard ExoticOverride(AbstractCard __result, AbstractCard.CardType type) {
            if(ModConfig.ExoticCards && __result.rarity == AbstractCard.CardRarity.RARE && !srcExoticCardPool.isEmpty()) {
                if (ExoticChance()) {
                    ArrayList<AbstractCard> ExoticNotHealing = new ArrayList<>();
                    for (AbstractCard c : srcExoticCardPool.group) {
                        if (!c.hasTag(AbstractCard.CardTags.HEALING) && c.type == type) {
                            ExoticNotHealing.add(c);
                        }
                    }
                    if (ExoticNotHealing.isEmpty()) return __result;
                    return ExoticNotHealing.get(cardRandomRng.random(ExoticNotHealing.size() - 1));
                }
            }
            return __result;
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "returnTrulyRandomCardFromAvailable", paramtypez = {AbstractCard.class, Random.class})
    public static class FalselyRandomButNotThatGuy {

        @SpireInsertPatch(rloc = 2, localvars = {"list"})
        public static void ShopInsert(AbstractCard prohibited, ArrayList<AbstractCard> list) {
            if (ModConfig.ShopCards && !srcShopCardPool.isEmpty()) {
                for (AbstractCard c : srcShopCardPool.group) {
                    if (!Objects.equals(c.cardID, prohibited.cardID)) {
                        list.add(c);
                    }
                }
            }
        }

        @SpirePostfixPatch
        public static AbstractCard ExoticOverride(AbstractCard __result, AbstractCard prohibited) {
            if(ModConfig.ExoticCards && __result.rarity == AbstractCard.CardRarity.RARE && !srcExoticCardPool.isEmpty()) {
                if (ExoticChance()) {
                    ArrayList<AbstractCard> ExoticNotProhibited = new ArrayList<>();
                    for (AbstractCard c : srcExoticCardPool.group) {
                        if (!Objects.equals(c.cardID, prohibited.cardID)) {
                            ExoticNotProhibited.add(c);
                        }
                    }
                    if (ExoticNotProhibited.isEmpty()) return __result;
                    return ExoticNotProhibited.get(cardRandomRng.random(ExoticNotProhibited.size() - 1));
                }
            }
            return __result;
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "returnRandomCard")
    public static class WhatEvenIsThisAnymore {

        @SpirePostfixPatch
        public static AbstractCard ExoticOverride(AbstractCard __result) {
            if(ModConfig.ExoticCards && __result.rarity == AbstractCard.CardRarity.RARE && !srcExoticCardPool.isEmpty()) {
                if (ExoticChance()) {
                    return srcExoticCardPool.getRandomCard(true);
                }
            }
            return __result;
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "srcTransformCard")
    public static class HardcodedBullshit {

        @SpirePrefixPatch
        public static SpireReturn<Void> Transform(AbstractCard c) {
            if (c.rarity == CustomRarity.SHOP) {
                srcShopCardPool.removeCard(c.cardID);
                if (srcShopCardPool.isEmpty()) {
                    srcShopCardPool.addToTop(c.makeCopy());
                    AbstractDungeon.transformedCard = srcShopCardPool.getRandomCard(false).makeCopy();
                    return SpireReturn.Return();
                }
                AbstractDungeon.transformedCard = srcShopCardPool.getRandomCard(false).makeCopy();
                srcShopCardPool.addToTop(c.makeCopy());
                return SpireReturn.Return();
            }
            if (c.rarity == CustomRarity.EXOTIC) {
                srcExoticCardPool.removeCard(c.cardID);
                if (srcExoticCardPool.isEmpty()) {
                    srcExoticCardPool.addToTop(c.makeCopy());
                    AbstractDungeon.transformedCard = srcExoticCardPool.getRandomCard(false).makeCopy();
                    return SpireReturn.Return();
                }
                AbstractDungeon.transformedCard = srcExoticCardPool.getRandomCard(false).makeCopy();
                srcExoticCardPool.addToTop(c.makeCopy());
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "initializeCardPools")
    public static class InitializeCustomPools {
        @SpirePrefixPatch
        public static void PoolClear() {
            shopCardPool.clear();
            exoticCardPool.clear();
        }
        // yayyy i love prefix and postfix so much i sure hope i dont have to do any insert patches!!!
        @SpirePostfixPatch
        public static void SrcPoolInit() {
            srcShopCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            srcExoticCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

            for(AbstractCard c : shopCardPool.group) {
                srcShopCardPool.addToBottom(c);
            }

            for(AbstractCard c : exoticCardPool.group) {
                srcExoticCardPool.addToBottom(c);
            }
        }
        // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmpPool"})
        public static void InitPools(ArrayList<AbstractCard> tmpPool) {
            for(AbstractCard c : tmpPool) {
                if (c.rarity == CustomRarity.SHOP) {
                    shopCardPool.addToTop(c);
                }
                if (c.rarity == CustomRarity.EXOTIC) {
                    exoticCardPool.addToTop(c);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "addColorlessCards");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}

// never make a rarity. definitely dont make two of them.
