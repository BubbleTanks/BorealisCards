package borealiscards.util;

import borealiscards.patches.adventuringpaintpatches.RandomPathEdgeRenderPatch;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

import java.util.ArrayList;
import java.util.List;

public class RandomPathSelector {
    private static final Color RANDOM_PATH_COLOR = Color.YELLOW.cpy();
    public static List<MapRoomNode> randomSelectedPath = new ArrayList<>();

    /**
     * Selects a random valid path from the current position to the boss node.
     * This should be called when entering a new Act.
     */
    public static void selectRandomPath() {
        clearRandomPath();

        if (AbstractDungeon.map == null || AbstractDungeon.map.isEmpty()) {
            return;
        }

        ArrayList<MapRoomNode> currentRow = AbstractDungeon.map.get(0);
        ArrayList<MapRoomNode> validStartNodes = new ArrayList<>();

        for (MapRoomNode node : currentRow) {
            if (node.hasEdges()) {
                validStartNodes.add(node);
            }
        }

        if (validStartNodes.isEmpty()) {
            return;
        }

        MapRoomNode currentNode = validStartNodes.get(AbstractDungeon.mapRng.random(validStartNodes.size() - 1));
        randomSelectedPath.add(currentNode);

        int currentY = 0;
        int maxY = AbstractDungeon.map.size() - 1;

        // Changed condition to currentY <= maxY to include the boss row
        while (currentY <= maxY) {
            ArrayList<MapRoomNode> nextValidNodes = getConnectedNodesInNextRow(currentNode, currentY + 1);

            if (nextValidNodes.isEmpty()) {
                break;
            }

            currentNode = nextValidNodes.get(AbstractDungeon.mapRng.random(nextValidNodes.size() - 1));
            randomSelectedPath.add(currentNode);
            currentY++;
        }
    }

    private static ArrayList<MapRoomNode> getConnectedNodesInNextRow(MapRoomNode fromNode, int targetY) {
        ArrayList<MapRoomNode> validNodes = new ArrayList<>();

        if (targetY >= AbstractDungeon.map.size()) {
            return validNodes;
        }

        ArrayList<MapRoomNode> targetRow = AbstractDungeon.map.get(targetY);

        for (MapRoomNode node : targetRow) {
            if (node != null && fromNode.isConnectedTo(node)) {
                validNodes.add(node);
            }
        }

        return validNodes;
    }

    /**
     * Hook to check if a node is on the random path.
     * Can be used by other systems that need to know about path membership.
     */
    public static boolean isOnPath(MapRoomNode node) {
        if (node == null) return false;

        // Check if this is a boss room (dynamically created, not in the map structure)
        if (isBossNode(node)) {
            if (AbstractDungeon.pathY.size() < 2) {
                return false;
            }

            // Get the second-to-last position (the node before entering boss)
            int lastY = AbstractDungeon.pathY.get(AbstractDungeon.pathY.size() - 2);
            int lastX = AbstractDungeon.pathX.get(AbstractDungeon.pathX.size() - 2);

            if (lastY >= AbstractDungeon.map.size()) {
                return false;
            }

            for (MapRoomNode mapNode : AbstractDungeon.map.get(lastY)) {
                if (mapNode.x == lastX) {
                    return randomSelectedPath.contains(mapNode);
                }
            }

            return false;
        }

        return randomSelectedPath.contains(node);
    }

    public static boolean isBossNode(MapRoomNode node) {
        // Boss nodes are created with x=-1 and aren't in the map structure
        // They also have MonsterRoomBoss as their room type
        return node.x == -1 ||
                (node.room != null && node.room instanceof MonsterRoomBoss);
    }

    public static void clearRandomPath() {
        randomSelectedPath.clear();
        RandomPathEdgeRenderPatch.clearSplatters();
    }

    public static Color getRandomPathColor() {
        return RANDOM_PATH_COLOR;
    }
}