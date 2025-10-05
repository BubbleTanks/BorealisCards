package borealiscards.patches.adventuringpaintpatches;

import borealiscards.relics.AdventuringPaint;
import borealiscards.util.RandomPathSelector;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;

public class AdventuringPaintStartOfActPatches {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateMap"
    )
    public static class GenerateMapPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (AbstractDungeon.player.hasRelic(AdventuringPaint.ID)) {
                RandomPathSelector.selectRandomPath();
            }
        }
    }

    @SpirePatch(
            clz = TheEnding.class,
            method = "generateSpecialMap"
    )
    public static class Act4Patch {
        @SpirePostfixPatch
        public static void Postfix(TheEnding __instance) {
            if (AbstractDungeon.player.hasRelic(AdventuringPaint.ID)) {
                RandomPathSelector.selectRandomPath();
            }
        }
    }
}