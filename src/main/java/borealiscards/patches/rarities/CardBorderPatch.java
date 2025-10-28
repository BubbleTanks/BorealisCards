package borealiscards.patches.rarities;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class CardBorderPatch {

    public static Color BANNER_COLOR_SHOP = CardHelper.getColor(101, 240, 131);
    public static Color IMG_FRAME_COLOR_SHOP = CardHelper.getColor(73, 191, 110);

    public static Color BANNER_COLOR_EXOTIC = CardHelper.getColor(162, 118, 196);
    public static Color IMG_FRAME_COLOR_EXOTIC = CardHelper.getColor(143, 108, 171);

    @SpirePatch2(clz = AbstractCard.class, method = "createCardImage")
    public static class CardBorder {
        @SpirePostfixPatch
        public static void createColor(AbstractCard __instance, @ByRef Color[] ___bannerColor, @ByRef Color[] ___imgFrameColor) {

            if (__instance.rarity == CustomRarity.SHOP) {
                ___bannerColor[0] = BANNER_COLOR_SHOP.cpy();
                ___imgFrameColor[0] = IMG_FRAME_COLOR_SHOP.cpy();
            }

            if (__instance.rarity == CustomRarity.EXOTIC) {
                ___bannerColor[0] = BANNER_COLOR_EXOTIC.cpy();
                ___imgFrameColor[0] = IMG_FRAME_COLOR_EXOTIC.cpy();
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderAttackPortrait")
    public static class AttackBorder {

        public static TextureRegion shopAttackFrame = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/frame_attack_shop.png"));
        public static TextureAtlas.AtlasRegion shopAttackAtlas = new TextureAtlas.AtlasRegion(shopAttackFrame.getTexture(), shopAttackFrame.getRegionX(), shopAttackFrame.getRegionY(), shopAttackFrame.getRegionWidth(), shopAttackFrame.getRegionHeight());

        public static TextureRegion exoticAttackFrame = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/frame_attack_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticAttackAtlas = new TextureAtlas.AtlasRegion(exoticAttackFrame.getTexture(), exoticAttackFrame.getRegionX(), exoticAttackFrame.getRegionY(), exoticAttackFrame.getRegionWidth(), exoticAttackFrame.getRegionHeight());

        @SpirePostfixPatch
        public static void assignAttackBorder(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor) {

            if(__instance.rarity == CustomRarity.SHOP) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, shopAttackAtlas, x, y
                );
            }

            if(__instance.rarity == CustomRarity.EXOTIC) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, exoticAttackAtlas, x, y
                );
            }

        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderSkillPortrait")
    public static class SkillBorder {

        public static TextureRegion shopSkillFrame = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/frame_skill_shop.png"));
        public static TextureAtlas.AtlasRegion shopSkillAtlas = new TextureAtlas.AtlasRegion(shopSkillFrame.getTexture(), shopSkillFrame.getRegionX(), shopSkillFrame.getRegionY(), shopSkillFrame.getRegionWidth(), shopSkillFrame.getRegionHeight());

        public static TextureRegion exoticSkillFrame = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/frame_skill_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticSkillAtlas = new TextureAtlas.AtlasRegion(exoticSkillFrame.getTexture(), exoticSkillFrame.getRegionX(), exoticSkillFrame.getRegionY(), exoticSkillFrame.getRegionWidth(), exoticSkillFrame.getRegionHeight());

        @SpirePostfixPatch
        public static void assignSkillBorder(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor) {

            if(__instance.rarity == CustomRarity.SHOP) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, shopSkillAtlas, x, y
                );
            }

            if(__instance.rarity == CustomRarity.EXOTIC) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, exoticSkillAtlas, x, y
                );
            }

        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderPowerPortrait")
    public static class PowerBorder {

        public static TextureRegion shopPowerFrame = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/frame_power_shop.png"));
        public static TextureAtlas.AtlasRegion shopPowerAtlas = new TextureAtlas.AtlasRegion(shopPowerFrame.getTexture(), shopPowerFrame.getRegionX(), shopPowerFrame.getRegionY(), shopPowerFrame.getRegionWidth(), shopPowerFrame.getRegionHeight());

        public static TextureRegion exoticPowerFrame = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/frame_power_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticPowerAtlas = new TextureAtlas.AtlasRegion(exoticPowerFrame.getTexture(), exoticPowerFrame.getRegionX(), exoticPowerFrame.getRegionY(), exoticPowerFrame.getRegionWidth(), exoticPowerFrame.getRegionHeight());

        @SpirePostfixPatch
        public static void assignPowerBorder(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor) {

            if(__instance.rarity == CustomRarity.SHOP) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, shopPowerAtlas, x, y
                );
            }

            if(__instance.rarity == CustomRarity.EXOTIC) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, exoticPowerAtlas, x, y
                );
            }

        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderBannerImage")
    public static class CardBanner {

        public static TextureRegion shopBanner = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/banner_shop.png"));
        public static TextureAtlas.AtlasRegion shopBannerAtlas = new TextureAtlas.AtlasRegion(shopBanner.getTexture(), shopBanner.getRegionX(), shopBanner.getRegionY(), shopBanner.getRegionWidth(), shopBanner.getRegionHeight());

        public static TextureRegion exoticBanner = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/banner_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticBannerAtlas = new TextureAtlas.AtlasRegion(exoticBanner.getTexture(), exoticBanner.getRegionX(), exoticBanner.getRegionY(), exoticBanner.getRegionWidth(), exoticBanner.getRegionHeight());

        @SpirePostfixPatch
        public static void assignBanner(AbstractCard __instance, SpriteBatch sb, float drawX, float drawY, Color ___renderColor) {

            if(__instance.rarity == CustomRarity.SHOP) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, shopBannerAtlas, drawX, drawY
                );
            }

            if(__instance.rarity == CustomRarity.EXOTIC) {
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, ___renderColor, exoticBannerAtlas, drawX, drawY
                );
            }

        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderDynamicFrame")
    public static class DynamicFrame {

        public static TextureRegion shopDynamicLeft = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/shop_left.png"));
        public static TextureAtlas.AtlasRegion shopLeftAtlas = new TextureAtlas.AtlasRegion(shopDynamicLeft.getTexture(), shopDynamicLeft.getRegionX(), shopDynamicLeft.getRegionY(), shopDynamicLeft.getRegionWidth(), shopDynamicLeft.getRegionHeight());
        public static TextureRegion shopDynamicRight = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/shop_right.png"));
        public static TextureAtlas.AtlasRegion shopRightAtlas = new TextureAtlas.AtlasRegion(shopDynamicRight.getTexture(), shopDynamicRight.getRegionX(), shopDynamicRight.getRegionY(), shopDynamicRight.getRegionWidth(), shopDynamicRight.getRegionHeight());
        public static TextureRegion shopDynamicCenter = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/shop_center.png"));
        public static TextureAtlas.AtlasRegion shopCenterAtlas = new TextureAtlas.AtlasRegion(shopDynamicCenter.getTexture(), shopDynamicCenter.getRegionX(), shopDynamicCenter.getRegionY(), shopDynamicCenter.getRegionWidth(), shopDynamicCenter.getRegionHeight());

        public static TextureRegion exoticDynamicLeft = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/exotic_left.png"));
        public static TextureAtlas.AtlasRegion exoticLeftAtlas = new TextureAtlas.AtlasRegion(exoticDynamicLeft.getTexture(), exoticDynamicLeft.getRegionX(), exoticDynamicLeft.getRegionY(), exoticDynamicLeft.getRegionWidth(), exoticDynamicLeft.getRegionHeight());
        public static TextureRegion exoticDynamicRight = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/exotic_right.png"));
        public static TextureAtlas.AtlasRegion exoticRightAtlas = new TextureAtlas.AtlasRegion(exoticDynamicRight.getTexture(), exoticDynamicRight.getRegionX(), exoticDynamicRight.getRegionY(), exoticDynamicRight.getRegionWidth(), exoticDynamicRight.getRegionHeight());
        public static TextureRegion exoticDynamicCenter = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/512/exotic_center.png"));
        public static TextureAtlas.AtlasRegion exoticCenterAtlas = new TextureAtlas.AtlasRegion(exoticDynamicCenter.getTexture(), exoticDynamicCenter.getRegionX(), exoticDynamicCenter.getRegionY(), exoticDynamicCenter.getRegionWidth(), exoticDynamicCenter.getRegionHeight());

        @SpirePostfixPatch
        public static void assignDynamics(AbstractCard __instance, SpriteBatch sb, float x, float y, float typeOffset, float typeWidth) {
            if (!(typeWidth <= 1.1F)) {

                if (__instance.rarity == CustomRarity.SHOP) {
                    ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class).invoke(
                            __instance, sb, shopLeftAtlas, x, y, -typeOffset, 1.0F
                    );
                    ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class).invoke(
                            __instance, sb, shopRightAtlas, x, y, typeOffset, 1.0F
                    );
                    ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class).invoke(
                            __instance, sb, shopCenterAtlas, x, y, 0.0F, typeWidth
                    );
                }

                if (__instance.rarity == CustomRarity.EXOTIC) {
                    ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class).invoke(
                            __instance, sb, exoticLeftAtlas, x, y, -typeOffset, 1.0F
                    );
                    ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class).invoke(
                            __instance, sb, exoticRightAtlas, x, y, typeOffset, 1.0F
                    );
                    ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class).invoke(
                            __instance, sb, exoticCenterAtlas, x, y, 0.0F, typeWidth
                    );
                }

            }
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderFrame")
    public static class SingleFrame {

        public static TextureRegion shopAttackFrameL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/frame_attack_shop.png"));
        public static TextureAtlas.AtlasRegion shopAttackAtlasL = new TextureAtlas.AtlasRegion(shopAttackFrameL.getTexture(), shopAttackFrameL.getRegionX(), shopAttackFrameL.getRegionY(), shopAttackFrameL.getRegionWidth(), shopAttackFrameL.getRegionHeight());
        public static TextureRegion shopSkillFrameL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/frame_skill_shop.png"));
        public static TextureAtlas.AtlasRegion shopSkillAtlasL = new TextureAtlas.AtlasRegion(shopSkillFrameL.getTexture(), shopSkillFrameL.getRegionX(), shopSkillFrameL.getRegionY(), shopSkillFrameL.getRegionWidth(), shopSkillFrameL.getRegionHeight());
        public static TextureRegion shopPowerFrameL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/frame_power_shop.png"));
        public static TextureAtlas.AtlasRegion shopPowerAtlasL = new TextureAtlas.AtlasRegion(shopPowerFrameL.getTexture(), shopPowerFrameL.getRegionX(), shopPowerFrameL.getRegionY(), shopPowerFrameL.getRegionWidth(), shopPowerFrameL.getRegionHeight());

        public static TextureRegion exoticAttackFrameL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/frame_attack_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticAttackAtlasL = new TextureAtlas.AtlasRegion(exoticAttackFrameL.getTexture(), exoticAttackFrameL.getRegionX(), exoticAttackFrameL.getRegionY(), exoticAttackFrameL.getRegionWidth(), exoticAttackFrameL.getRegionHeight());
        public static TextureRegion exoticSkillFrameL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/frame_skill_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticSkillAtlasL = new TextureAtlas.AtlasRegion(exoticSkillFrameL.getTexture(), exoticSkillFrameL.getRegionX(), exoticSkillFrameL.getRegionY(), exoticSkillFrameL.getRegionWidth(), exoticSkillFrameL.getRegionHeight());
        public static TextureRegion exoticPowerFrameL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/frame_power_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticPowerAtlasL = new TextureAtlas.AtlasRegion(exoticPowerFrameL.getTexture(), exoticPowerFrameL.getRegionX(), exoticPowerFrameL.getRegionY(), exoticPowerFrameL.getRegionWidth(), exoticPowerFrameL.getRegionHeight());

        @SpireInsertPatch(rloc = 91, localvars = {"tmpImg"})
        public static void cardFrame(AbstractCard ___card, @ByRef TextureAtlas.AtlasRegion[] tmpImg) {

            if(___card.rarity == CustomRarity.SHOP) {
                switch (___card.type) {
                    case ATTACK:
                        tmpImg[0] = shopAttackAtlasL;
                        break;
                    case POWER:
                        tmpImg[0] = shopPowerAtlasL;
                        break;
                    default:
                        tmpImg[0] = shopSkillAtlasL;
                        break;
                }
            }

            if(___card.rarity == CustomRarity.EXOTIC) {
                switch (___card.type) {
                    case ATTACK:
                        tmpImg[0] = exoticAttackAtlasL;
                        break;
                    case POWER:
                        tmpImg[0] = exoticPowerAtlasL;
                        break;
                    default:
                        tmpImg[0] = exoticSkillAtlasL;
                        break;
                }
            }

        }


    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderCardBanner")
    public static class SingleBanner {
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "rarity");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        public static TextureRegion shopBannerL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/banner_shop.png"));
        public static TextureAtlas.AtlasRegion shopBannerAtlasL = new TextureAtlas.AtlasRegion(shopBannerL.getTexture(), shopBannerL.getRegionX(), shopBannerL.getRegionY(), shopBannerL.getRegionWidth(), shopBannerL.getRegionHeight());

        public static TextureRegion exoticBannerL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/banner_exotic.png"));
        public static TextureAtlas.AtlasRegion exoticBannerAtlasL = new TextureAtlas.AtlasRegion(exoticBannerL.getTexture(), exoticBannerL.getRegionX(), exoticBannerL.getRegionY(), exoticBannerL.getRegionWidth(), exoticBannerL.getRegionHeight());

        @SpireInsertPatch(locator = Locator.class, localvars = {"tmpImg"})
        public static void cardBanner(AbstractCard ___card, @ByRef TextureAtlas.AtlasRegion[] tmpImg) {

            if(___card.rarity == CustomRarity.SHOP) {
                tmpImg[0] = shopBannerAtlasL;
            }

            if(___card.rarity == CustomRarity.EXOTIC) {
                tmpImg[0] = exoticBannerAtlasL;
            }

        }


    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderDynamicFrame")
    public static class SingleDynamic {
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "rarity");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        public static TextureRegion shopDynamicLeftL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/shop_left.png"));
        public static TextureAtlas.AtlasRegion shopLeftAtlasL = new TextureAtlas.AtlasRegion(shopDynamicLeftL.getTexture(), shopDynamicLeftL.getRegionX(), shopDynamicLeftL.getRegionY(), shopDynamicLeftL.getRegionWidth(), shopDynamicLeftL.getRegionHeight());
        public static TextureRegion shopDynamicRightL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/shop_right.png"));
        public static TextureAtlas.AtlasRegion shopRightAtlasL = new TextureAtlas.AtlasRegion(shopDynamicRightL.getTexture(), shopDynamicRightL.getRegionX(), shopDynamicRightL.getRegionY(), shopDynamicRightL.getRegionWidth(), shopDynamicRightL.getRegionHeight());
        public static TextureRegion shopDynamicCenterL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/shop_center.png"));
        public static TextureAtlas.AtlasRegion shopCenterAtlasL = new TextureAtlas.AtlasRegion(shopDynamicCenterL.getTexture(), shopDynamicCenterL.getRegionX(), shopDynamicCenterL.getRegionY(), shopDynamicCenterL.getRegionWidth(), shopDynamicCenterL.getRegionHeight());

        public static TextureRegion exoticDynamicLeftL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/exotic_left.png"));
        public static TextureAtlas.AtlasRegion exoticLeftAtlasL = new TextureAtlas.AtlasRegion(exoticDynamicLeftL.getTexture(), exoticDynamicLeftL.getRegionX(), exoticDynamicLeftL.getRegionY(), exoticDynamicLeftL.getRegionWidth(), exoticDynamicLeftL.getRegionHeight());
        public static TextureRegion exoticDynamicRightL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/exotic_right.png"));
        public static TextureAtlas.AtlasRegion exoticRightAtlasL = new TextureAtlas.AtlasRegion(exoticDynamicRightL.getTexture(), exoticDynamicRightL.getRegionX(), exoticDynamicRightL.getRegionY(), exoticDynamicRightL.getRegionWidth(), exoticDynamicRightL.getRegionHeight());
        public static TextureRegion exoticDynamicCenterL = new TextureRegion(ImageMaster.loadImage("borealiscards/images/cardui/1024/exotic_center.png"));
        public static TextureAtlas.AtlasRegion exoticCenterAtlasL = new TextureAtlas.AtlasRegion(exoticDynamicCenterL.getTexture(), exoticDynamicCenterL.getRegionX(), exoticDynamicCenterL.getRegionY(), exoticDynamicCenterL.getRegionWidth(), exoticDynamicCenterL.getRegionHeight());

        @SpireInsertPatch(locator = Locator.class)
        public static void cardDynamic(SingleCardViewPopup __instance, SpriteBatch sb, float typeOffset, float typeWidth, AbstractCard ___card) {

            if(___card.rarity == CustomRarity.SHOP) {
                ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, shopLeftAtlasL, -typeOffset, 1.0F
                );
                ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, shopRightAtlasL, typeOffset, 1.0F
                );
                ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, shopCenterAtlasL, 0.0F, typeWidth
                );
            }

            if(___card.rarity == CustomRarity.EXOTIC) {
                ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, exoticLeftAtlasL, -typeOffset, 1.0F
                );
                ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, exoticRightAtlasL, typeOffset, 1.0F
                );
                ReflectionHacks.privateMethod(AbstractCard.class, "dynamicFrameRenderHelper", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(
                        __instance, sb, exoticCenterAtlasL, 0.0F, typeWidth
                );
            }

        }
    }



}
