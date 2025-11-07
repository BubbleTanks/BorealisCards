package borealiscards.patches;

import borealiscards.powers.IridiumToxinsPower;
import borealiscards.relics.Blacklight;
import borealiscards.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class BlacklightPatch {

    @SpirePatch2(clz = PoisonPower.class, method = SpirePatch.CONSTRUCTOR)
    public static class PoisonConstructor {
        @SpirePostfixPatch
        public static void poisonBlacklight(PoisonPower __instance) {
            if (AbstractDungeon.player.hasRelic(Blacklight.ID) && __instance.owner != AbstractDungeon.player) {
                if (!__instance.owner.hasPower(PoisonPower.POWER_ID)) {
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (r.relicId == Blacklight.ID) {
                            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, r));
                            r.flash();
                        }
                    }
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            __instance.atStartOfTurn();
                            this.isDone = true;
                        }
                    });
                }

                if (!AbstractDungeon.player.hasPower(IridiumToxinsPower.POWER_ID)) {

                    String unPrefixed = "PurplePoison";
                    Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
                    Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
                    if (hiDefImage != null) {
                        __instance.region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                        if (normalTexture != null)
                            __instance.region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
                    }

                }
            }

            if (AbstractDungeon.player.hasPower(IridiumToxinsPower.POWER_ID)) {

                String unPrefixed = "YellowPoison";
                Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
                Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
                if (hiDefImage != null) {
                    __instance.region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                    if (normalTexture != null)
                        __instance.region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
                }

            }

        }
    }

    @SpirePatch2(clz = PoisonPower.class, method = "stackPower")
    public static class PoisonStack {
        @SpirePostfixPatch
        public static void poisonBlacklight2(PoisonPower __instance) {
            if (AbstractDungeon.player.hasRelic(Blacklight.ID) && __instance.owner != AbstractDungeon.player) {
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r.relicId == Blacklight.ID) {
                        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, r));
                        r.flash();
                    }
                }

                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        __instance.atStartOfTurn();
                        this.isDone = true;
                    }
                });
            }
        }
    }

    @SpirePatch2(clz = PoisonLoseHpAction.class, method = "update")
    public static class PoisonAction {
        @SpireInsertPatch(locator = Locator.class, localvars = {"p"})
        public static void blacklightReduction(AbstractPower p, AbstractGameAction __instance) {
            if (AbstractDungeon.player.hasRelic(Blacklight.ID) && p.owner != AbstractDungeon.player) {
                if (p.amount > 1) {
                    p.amount--;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPower.class, "amount");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = PoisonPower.class, method = "updateDescription")
    public static class PoisonNewDescription {
        @SpirePostfixPatch
        public static void blacklightDescription(PoisonPower __instance) {
            if (AbstractDungeon.player.hasRelic(Blacklight.ID) && __instance.owner != null && __instance.owner != AbstractDungeon.player) {

                PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings("borealiscards:BlacklightPoison");;;;;;;

                __instance.description = strings.DESCRIPTIONS[0] + __instance.amount + strings.DESCRIPTIONS[1];
            }
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "renderGreenHealthBar")
    public static class PoisonHealthBar {
        @SpireInsertPatch(rloc = 3)
        public static void blacklightColor(SpriteBatch sb) {

            if (!AbstractDungeon.player.hasPower(IridiumToxinsPower.POWER_ID)) {
                if (AbstractDungeon.player.hasRelic(Blacklight.ID)) {
                    sb.setColor(0.60F, 0.23F, 1.0F, 1.0F);
                }
            } else {
                sb.setColor(1.0F, 0.94F, 0.48F, 1.0F);
            }
        }
    }
}
