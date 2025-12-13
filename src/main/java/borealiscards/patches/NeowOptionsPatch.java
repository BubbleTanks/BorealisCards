package borealiscards.patches;

import borealiscards.BorealisCards;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.patches.rarities.PoolPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class NeowOptionsPatch {

    @SpireEnum
    public static NeowReward.NeowRewardType SHOPCARD;

    @SpireEnum
    public static NeowReward.NeowRewardType EXOTICSWAP;

    public static UIStrings NEOWSTRING() {
        return CardCrawlGame.languagePack.getUIString(BorealisCards.makeID("NeowRewards"));
    }

    @SpirePatch2(clz = NeowReward.class, method = "getRewardOptions")
    public static class GetOptionsPatch {
        @SpireInsertPatch(locator = Locator1.class, localvars = {"rewardOptions"})
        public static void spawnNewShopCardReward(ArrayList<NeowReward.NeowRewardDef> rewardOptions) {
            if (PoolPatch.shopCardPool.size() >= 3) {
                rewardOptions.add(new NeowReward.NeowRewardDef(SHOPCARD, NEOWSTRING().TEXT[0]));
            }
        }

        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(NeowReward.NeowRewardDrawbackDef.class, "type");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        @SpireInsertPatch(locator = Locator2.class, localvars = {"rewardOptions"})
        public static SpireReturn<ArrayList<NeowReward.NeowRewardDef>> spawnNewExoticCardSwap(ArrayList<NeowReward.NeowRewardDef> rewardOptions) {
            if (!PoolPatch.exoticCardPool.isEmpty() && NeowEvent.rng.random(0,49) == 0) {
                rewardOptions.add(new NeowReward.NeowRewardDef(EXOTICSWAP, NEOWSTRING().TEXT[1]));
                return SpireReturn.Return(rewardOptions);
            }
            return SpireReturn.Continue();
        }

        private static class Locator2 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(NeowReward.class, "UNIQUE_REWARDS");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = NeowReward.class, method = "activate")
    public static class RunOptionsPatch {
        @SpireInsertPatch(locator = Locator1.class)
        public static void triggerNewRewards(NeowReward __instance) {
            if (__instance.type == SHOPCARD) {
                AbstractDungeon.cardRewardScreen.open(getRewardCards(), null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
            }
            if (__instance.type == EXOTICSWAP) {
                AbstractDungeon.player.loseRelic(((AbstractRelic)AbstractDungeon.player.relics.get(0)).relicId);
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(CustomRarity.EXOTIC, NeowEvent.rng).makeCopy(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }

        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardCrawlGame.class, "metricData");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    public static ArrayList<AbstractCard> getRewardCards() {
        ArrayList<AbstractCard> retVal = new ArrayList();
        int numCards = 3;

        for(int i = 0; i < numCards; ++i) {

            AbstractCard card = PoolPatch.shopCardPool.getRandomCard(NeowEvent.rng);


            while(retVal.contains(card)) {
                card = PoolPatch.shopCardPool.getRandomCard(NeowEvent.rng);
            }

            retVal.add(card);
        }

        ArrayList<AbstractCard> retVal2 = new ArrayList();

        for(AbstractCard c : retVal) {
            retVal2.add(c.makeCopy());
        }

        return retVal2;
    }
}
