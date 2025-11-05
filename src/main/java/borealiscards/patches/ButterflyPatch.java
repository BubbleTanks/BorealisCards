package borealiscards.patches;

import borealiscards.relics.SleepingButterfly;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WingBoots;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ButterflyPatch {

    @SpirePatch2(clz = MapRoomNode.class, method = "wingedIsConnectedTo")
    public static class ButterflyCheck {
        @SpireInsertPatch(rloc = 1, localvars = {"edge"})
        public static SpireReturn<Boolean> butterflyReturn(MapRoomNode node, MapEdge edge) {
            if (node.y == edge.dstY && player.hasRelic(SleepingButterfly.ID) && ((SleepingButterfly) player.getRelic(SleepingButterfly.ID)).butterflyRest) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = MapRoomNode.class, method = "update")
    public static class ButterflyBoots {
        @SpireInsertPatch(locator = Locator.class)
        public static void bootUnuse() {
            AbstractRelic butterfly = player.getRelic(SleepingButterfly.ID);
            if (butterfly instanceof SleepingButterfly && ((SleepingButterfly)butterfly).butterflyRest)
            {
                AbstractRelic wb = player.getRelic(WingBoots.ID);
                if (wb != null) wb.counter++;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getRelic");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

}
