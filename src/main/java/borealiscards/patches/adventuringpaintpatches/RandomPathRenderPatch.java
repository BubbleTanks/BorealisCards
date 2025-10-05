package borealiscards.patches.adventuringpaintpatches;

import borealiscards.relics.AdventuringPaint;
import borealiscards.util.RandomPathSelector;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

@SpirePatch(
        clz = MapRoomNode.class,
        method = "render"
)
public class RandomPathRenderPatch {
    @SpirePrefixPatch
    public static void Prefix(MapRoomNode __instance, SpriteBatch sb) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic(AdventuringPaint.ID)) {
            return;
        }

        if (RandomPathSelector.isOnPath(__instance)) {
            if (__instance.room == null || !(__instance.room instanceof MonsterRoomBoss)) {
                __instance.color = RandomPathSelector.getRandomPathColor().cpy();
            }
        }
    }
}