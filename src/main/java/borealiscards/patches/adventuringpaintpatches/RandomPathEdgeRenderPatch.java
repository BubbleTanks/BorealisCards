package borealiscards.patches.adventuringpaintpatches;

import borealiscards.relics.AdventuringPaint;
import borealiscards.util.PaintSplatter;
import borealiscards.util.RandomPathSelector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(
        clz = MapEdge.class,
        method = "render"
)
public class RandomPathEdgeRenderPatch {

    private static HashMap<MapEdge, ArrayList<PaintSplatter>> edgeSplatters = new HashMap<>();

    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(MapEdge __instance, SpriteBatch sb) {

        if (!AbstractDungeon.player.hasRelic(AdventuringPaint.ID)) {
            return SpireReturn.Continue();
        }

        MapRoomNode srcNode = getNodeAtPosition(__instance.srcX, __instance.srcY);
        MapRoomNode dstNode = getNodeAtPosition(__instance.dstX, __instance.dstY);

        // Check if BOTH nodes are on the path (for regular edges)
        // OR if source is on path and destination is boss (for final edge)
        boolean isBothOnPath = srcNode != null && dstNode != null &&
                RandomPathSelector.isOnPath(srcNode) &&
                RandomPathSelector.isOnPath(dstNode);

        boolean isPathToBoss = srcNode != null && RandomPathSelector.isOnPath(srcNode) &&
                (dstNode == null || (dstNode.room instanceof MonsterRoomBoss));

        if (isBothOnPath || isPathToBoss) {
            // Generate splatters if we haven't already for this edge
            if (!edgeSplatters.containsKey(__instance)) {
                generateSplatters(__instance, srcNode, dstNode);
            }
            renderThickLine(sb, __instance, srcNode, dstNode);
            return SpireReturn.Return(null);
        }

        return SpireReturn.Continue();
    }

    private static void generateSplatters(MapEdge edge, MapRoomNode srcNode, MapRoomNode dstNode) {
        float SPACE_X = Settings.isMobile ? 140.8F * Settings.xScale : 128.0F * Settings.xScale;
        float OFFSET_Y = 180.0F * Settings.scale;

        float srcX = edge.srcX * SPACE_X + MapRoomNode.OFFSET_X + srcNode.offsetX;
        float srcY = edge.srcY * Settings.MAP_DST_Y + OFFSET_Y + srcNode.offsetY;

        float dstX = edge.dstX * SPACE_X + MapRoomNode.OFFSET_X;
        float dstY = edge.dstY * Settings.MAP_DST_Y + OFFSET_Y;

        // Only add node offsets if we have a destination node (not boss)
        if (dstNode != null) {
            dstX += dstNode.offsetX;
            dstY += dstNode.offsetY;
        }

        float distance = (float) Math.sqrt((dstX - srcX) * (dstX - srcX) + (dstY - srcY) * (dstY - srcY));

        ArrayList<PaintSplatter> splatters = new ArrayList<>();
        int numSplatters = (int)(distance / (40.0f * Settings.scale)) + MathUtils.random(2, 4);

        for (int i = 0; i < numSplatters; i++) {
            float t = MathUtils.random(0.2f, 0.8f);
            float x = srcX + (dstX - srcX) * t;
            float y = srcY + (dstY - srcY) * t;

            float perpOffset = MathUtils.random(-15f, 15f) * Settings.scale;
            float angle = (float) Math.atan2(dstY - srcY, dstX - srcX);
            x += Math.cos(angle + Math.PI / 2) * perpOffset;
            y += Math.sin(angle + Math.PI / 2) * perpOffset;

            splatters.add(new PaintSplatter(x, y));
        }

        edgeSplatters.put(edge, splatters);
    }

    private static void renderThickLine(SpriteBatch sb, MapEdge edge, MapRoomNode srcNode, MapRoomNode dstNode) {
        float SPACE_X = Settings.isMobile ? 140.8F * Settings.xScale : 128.0F * Settings.xScale;
        float OFFSET_Y = 180.0F * Settings.scale;

        float srcX = edge.srcX * SPACE_X + MapRoomNode.OFFSET_X + srcNode.offsetX;
        float srcY = edge.srcY * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY + srcNode.offsetY;

        float dstX = edge.dstX * SPACE_X + MapRoomNode.OFFSET_X;
        float dstY = edge.dstY * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY;

        // Only add node offsets if we have a destination node (not boss)
        if (dstNode != null) {
            dstX += dstNode.offsetX;
            dstY += dstNode.offsetY;
        }

        float dx = dstX - srcX;
        float dy = dstY - srcY;
        float fullDistance = (float) Math.sqrt(dx * dx + dy * dy);
        float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

        // Only shorten the line if connecting to boss (and boss is off-map)
        float distance = fullDistance;
        if (dstNode == null) {
            // Boss edge in normal acts - use the boss radius
            float BOSS_RADIUS = 164.0F * Settings.scale;
            distance = fullDistance - BOSS_RADIUS;
        }

        float thickness = 8.0f * Settings.scale;

        // Render splatters
        Color splatterColor = RandomPathSelector.getRandomPathColor().cpy();
        splatterColor.r = Math.min(1.0f, splatterColor.r * 1.1f);
        splatterColor.g = Math.min(1.0f, splatterColor.g * 0.95f);
        splatterColor.b = Math.min(1.0f, splatterColor.b * 0.5f);

        ArrayList<PaintSplatter> splatters = edgeSplatters.get(edge);
        if (splatters != null) {
            for (PaintSplatter splatter : splatters) {
                for (PaintSplatter.SplatterBlob blob : splatter.blobs) {
                    sb.setColor(splatterColor);
                    float blobSize = 25.0f * Settings.scale * blob.size;

                    sb.draw(
                            ImageMaster.POTION_SPHERE_LIQUID,
                            splatter.x + blob.offsetX - blobSize / 2,
                            splatter.y + blob.offsetY + DungeonMapScreen.offsetY - blobSize / 2,
                            blobSize / 2, blobSize / 2,
                            blobSize, blobSize,
                            1.0f, 1.0f,
                            0.0f,
                            0, 0, 256, 256,
                            false, false
                    );
                }
            }
        }

        // Render the main line
        sb.setColor(RandomPathSelector.getRandomPathColor());
        sb.draw(
                ImageMaster.WHITE_SQUARE_IMG,
                srcX, srcY - thickness / 2,
                0, thickness / 2,
                distance, thickness,
                1.0f, 1.0f,
                angle,
                0, 0,
                1, 1,
                false, false
        );
    }

    public static void clearSplatters() {
        edgeSplatters.clear();
    }

    private static MapRoomNode getNodeAtPosition(int x, int y) {
        if (AbstractDungeon.map == null || y >= AbstractDungeon.map.size()) {
            return null;
        }
        ArrayList<MapRoomNode> row = AbstractDungeon.map.get(y);
        for (MapRoomNode node : row) {
            if (node.x == x && node.y == y) {
                return node;
            }
        }
        return null;
    }
}