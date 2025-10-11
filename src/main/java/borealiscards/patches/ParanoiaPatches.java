package borealiscards.patches;

import borealiscards.relics.ParanoiasBox;
import borealiscards.rewards.ParanoiaReward;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ParanoiaPatches {
    @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class ParanoiaTransform {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CombatRewardScreen __instance) {
            if (AbstractDungeon.player.hasRelic(ParanoiasBox.ID) && AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                __instance.rewards.add(new ParanoiaReward());
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "event");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }
}
