package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.BehaviourTypeComparator;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.tiles.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import java.io.*;
import java.util.*;

/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @see de.uol.swp.server.gamelogic.Block
 * @since 2023-04-28
 */
public final class MapBuilder {

    public static List<AbstractMap> maps = new LinkedList<>();
    public static List<AbstractMap> testMaps = new LinkedList<>();
    private static final HashMap<String, Pair<Integer, Position>>
            mapStringToCheckpointNumberAndFirstPosition = new HashMap<>();
    private static final Logger LOG = LogManager.getLogger(MapBuilder.class);

    public static List<ArrayList> checkpointLocations = new LinkedList<>();
    public static ArrayList<int[][]> checkpointsMapOne = new ArrayList<>();
    public static ArrayList<int[][]> checkpointsMapTwo = new ArrayList<>();
    public static ArrayList<int[][]> checkpointsMapThree = new ArrayList<>();

    static {
        // define all three positions for each version
        // Map1
        Position[] versionPositionsMap1 = {
            new Position(4, 11), new Position(4, 3), new Position(3, 5)
        };
        for (int v = 1; v <= 3; v++) {
            for (int c = 2; c <= 6; c++) {
                mapStringToCheckpointNumberAndFirstPosition.put(
                        "MapOneV" + (v) + "C" + (c), new Pair<>(c, versionPositionsMap1[v - 1]));
            }
        }

        // Map2
        Position[] versionPositionsMap2 = {
            new Position(2, 4), new Position(9, 9), new Position(5, 5)
        };
        for (int v = 1; v <= 3; v++) {
            for (int c = 2; c <= 6; c++) {
                mapStringToCheckpointNumberAndFirstPosition.put(
                        "MapTwoV" + (v) + "C" + (c), new Pair<>(c, versionPositionsMap2[v - 1]));
            }
        }

        // Map3
        Position[] versionPositionsMap3 = {
            new Position(2, 2), new Position(3, 4), new Position(6, 8)
        };
        for (int v = 1; v <= 3; v++) {
            for (int c = 2; c <= 6; c++) {
                mapStringToCheckpointNumberAndFirstPosition.put(
                        "MapThreeV" + (v) + "C" + (c), new Pair<>(c, versionPositionsMap3[v - 1]));
            }
        }

        // Each TestMap 4 CHECKPOINTS
        int testCheckPoints = 4;

        // TestLaserMap
        mapStringToCheckpointNumberAndFirstPosition.put(
                "TestLaserMap", new Pair<>(testCheckPoints, new Position(5, 7)));
        // TestPusherMap
        mapStringToCheckpointNumberAndFirstPosition.put(
                "TestPusherMap", new Pair<>(testCheckPoints, new Position(6, 6)));
        // TestWallMap
        mapStringToCheckpointNumberAndFirstPosition.put(
                "TestWallMap", new Pair<>(testCheckPoints, new Position(6, 6)));
        // TestConveyorMap
        mapStringToCheckpointNumberAndFirstPosition.put(
                "TestConveyorMap", new Pair<>(testCheckPoints, new Position(6, 7)));
        // TestConveyorPressorAndPitMap
        mapStringToCheckpointNumberAndFirstPosition.put(
                "TestConveyorPressorAndPitMap", new Pair<>(testCheckPoints, new Position(6, 7)));
    }

    /**
     * Get the map with the given name
     *
     * @author Merden
     * @see de.uol.swp.server.gamelogic.Block
     * @see de.uol.swp.server.gamelogic.map.AbstractMap
     * @since 2023-06-06
     */
    public static Block[][] getMap(String mapPath) {
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(mapPath));
            Block[][] map = (Block[][]) objIn.readObject();
            objIn.close();
            return map;
        } catch (IOException | ClassNotFoundException IOExcept) {
            try {
                ObjectInputStream objIn =
                        new ObjectInputStream(new FileInputStream(mapPath.replace("server/", "")));
                Block[][] map = (Block[][]) objIn.readObject();
                objIn.close();
                return map;
            } catch (IOException | ClassNotFoundException IOExcept2) {
                LOG.warn(IOExcept2.getMessage());
                return null;
            }
        }
    }

    /**
     * Create some maps with all given parameters like checkpoints, behaviour types
     *
     * @author Merden
     * @see de.uol.swp.server.gamelogic.map.MapOne
     * @see de.uol.swp.server.gamelogic.map.MapTwo
     * @see de.uol.swp.server.gamelogic.map.MapThree
     * @see de.uol.swp.server.gamelogic.map.TestLaserMap
     * @see de.uol.swp.server.gamelogic.map.TestPusherMap
     * @see de.uol.swp.server.gamelogic.map.TestWallMap
     * @see de.uol.swp.server.gamelogic.map.TestConveyorMap
     * @since 2023-06-06
     */
    public static void main(String[] args) throws IOException {

        maps.add(new MapOne());
        maps.add(new MapTwo());
        maps.add(new MapThree());

        testMaps.add(new TestLaserMap());
        testMaps.add(new TestPusherMap());
        testMaps.add(new TestWallMap());
        testMaps.add(new TestConveyorMap());

        // Checkpoints added as {x1,x2,x3 ... x6} , {y1,y2,y3 ... y6}
        checkpointsMapOne.add(new int[][] {{4, 9}, {11, 3}});
        checkpointsMapOne.add(new int[][] {{4, 9, 2}, {11, 3, 2}});
        checkpointsMapOne.add(new int[][] {{4, 9, 2, 4}, {11, 3, 2, 5}});
        checkpointsMapOne.add(new int[][] {{4, 9, 2, 4, 8}, {11, 3, 2, 5, 6}});
        checkpointsMapOne.add(new int[][] {{4, 9, 2, 4, 8, 9}, {11, 3, 2, 5, 6, 9}});
        checkpointsMapOne.add(new int[][] {{4, 7}, {3, 5}});
        checkpointsMapOne.add(new int[][] {{4, 7, 3}, {3, 5, 10}});
        checkpointsMapOne.add(new int[][] {{4, 7, 3, 6}, {3, 5, 10, 8}});
        checkpointsMapOne.add(new int[][] {{4, 7, 3, 6, 0}, {3, 5, 10, 8, 5}});
        checkpointsMapOne.add(new int[][] {{4, 7, 3, 6, 0, 4}, {3, 5, 10, 8, 5, 2}});
        checkpointsMapOne.add(new int[][] {{3, 6}, {5, 8}});
        checkpointsMapOne.add(new int[][] {{3, 6, 8}, {5, 8, 3}});
        checkpointsMapOne.add(new int[][] {{3, 6, 8, 9}, {5, 8, 3, 8}});
        checkpointsMapOne.add(new int[][] {{3, 6, 8, 9, 5}, {5, 8, 3, 8, 11}});
        checkpointsMapOne.add(new int[][] {{3, 6, 8, 9, 5, 4}, {5, 8, 3, 8, 11, 9}});

        checkpointLocations.add(checkpointsMapOne);

        checkpointsMapTwo.add(new int[][] {{2, 9}, {4, 4}});
        checkpointsMapTwo.add(new int[][] {{2, 9, 5}, {4, 4, 6}});
        checkpointsMapTwo.add(new int[][] {{2, 9, 5, 4}, {4, 4, 6, 1}});
        checkpointsMapTwo.add(new int[][] {{2, 9, 5, 4, 9}, {4, 4, 6, 1, 9}});
        checkpointsMapTwo.add(new int[][] {{2, 9, 5, 4, 9, 4}, {4, 4, 6, 1, 9, 10}});
        checkpointsMapTwo.add(new int[][] {{9, 10}, {9, 4}});
        checkpointsMapTwo.add(new int[][] {{9, 10, 6}, {9, 4, 5}});
        checkpointsMapTwo.add(new int[][] {{9, 10, 6, 4}, {9, 4, 5, 3}});
        checkpointsMapTwo.add(new int[][] {{9, 10, 6, 4, 1}, {9, 4, 5, 3, 2}});
        checkpointsMapTwo.add(new int[][] {{9, 10, 6, 4, 1, 1}, {9, 4, 5, 3, 2, 10}});
        checkpointsMapTwo.add(new int[][] {{5, 7}, {5, 8}});
        checkpointsMapTwo.add(new int[][] {{5, 7, 10}, {5, 8, 4}});
        checkpointsMapTwo.add(new int[][] {{5, 7, 10, 4}, {5, 8, 4, 2}});
        checkpointsMapTwo.add(new int[][] {{5, 7, 10, 4, 1}, {5, 8, 4, 2, 7}});
        checkpointsMapTwo.add(new int[][] {{5, 7, 10, 4, 1, 6}, {5, 8, 4, 2, 7, 6}});

        checkpointLocations.add(checkpointsMapTwo);

        checkpointsMapThree.add(new int[][] {{2, 9}, {2, 9}});
        checkpointsMapThree.add(new int[][] {{2, 9, 2}, {2, 9, 9}});
        checkpointsMapThree.add(new int[][] {{2, 9, 2, 9}, {2, 9, 9, 2}});
        checkpointsMapThree.add(new int[][] {{2, 9, 2, 9, 1}, {2, 9, 9, 2, 8}});
        checkpointsMapThree.add(new int[][] {{2, 9, 2, 9, 1, 10}, {2, 9, 9, 2, 8, 5}});
        checkpointsMapThree.add(new int[][] {{3, 10}, {4, 1}});
        checkpointsMapThree.add(new int[][] {{3, 10, 8}, {4, 1, 4}});
        checkpointsMapThree.add(new int[][] {{3, 10, 8, 8}, {4, 1, 4, 8}});
        checkpointsMapThree.add(new int[][] {{3, 10, 8, 8, 1}, {4, 1, 4, 8, 8}});
        checkpointsMapThree.add(new int[][] {{3, 10, 8, 8, 1, 2}, {4, 1, 4, 8, 8, 2}});
        checkpointsMapThree.add(new int[][] {{6, 3}, {8, 2}});
        checkpointsMapThree.add(new int[][] {{6, 3, 8}, {8, 2, 3}});
        checkpointsMapThree.add(new int[][] {{6, 3, 8, 9}, {8, 2, 3, 8}});
        checkpointsMapThree.add(new int[][] {{6, 3, 8, 9, 5}, {8, 2, 3, 8, 11}});
        checkpointsMapThree.add(new int[][] {{6, 3, 8, 9, 5, 2}, {8, 2, 3, 8, 11, 9}});

        checkpointLocations.add(checkpointsMapThree);

        mapGen();
    }

    /**
     * Get the Strings for the checkpoints and the first position of the map
     *
     * @author Maria
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-30
     */
    public static Pair<Integer, Position> getMapStringToCheckpointNumberAndFirstPosition(
            String mapName) {
        LOG.warn("In getMapStringToCheckpointNumberAndFirstPosition");
        LOG.warn("gettting info for " + mapName);
        LOG.warn("---------> " + mapStringToCheckpointNumberAndFirstPosition.get(mapName));
        return mapStringToCheckpointNumberAndFirstPosition.get(mapName);
    }

    /**
     * Generate the maps
     *
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.Block
     * @see java.io
     * @since 2023-06-18
     */
    public static void mapGen() throws IOException {

        // Generate Normal Maps
        for (int mapCount = 0; mapCount < maps.size(); mapCount++) {
            for (int version = 0; version < 3; version++) { // Version
                for (int checkpoints = 0; checkpoints < 5; checkpoints++) { // Checkpoints
                    Block[][] originalMap = maps.get(mapCount).getMap();
                    Block[][] map =
                            mapGenExtracted(copyMap(originalMap), version, checkpoints, mapCount);
                    String path =
                            "server/src/main/resources/maps/"
                                    + maps.get(mapCount)
                                            .getClass()
                                            .getName()
                                            .replace("de.uol.swp.server.gamelogic.map.", "")
                                    + "V"
                                    + (version + 1)
                                    + "C"
                                    + (checkpoints + 2)
                                    + ".map";

                    if (new File(".").getAbsolutePath().endsWith("server\\.")) {
                        path = path.replace("server/", "");
                    }
                    ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(path));
                    objOut.writeObject(map);
                    objOut.flush();
                    objOut.close();
                }
            }
        }

        // Generate Test Maps
        for (AbstractMap maps : testMaps) {
            Block[][] map = mapGenExtracted(maps.getMap(), -1, 0, 0);
            String path =
                    "server/src/main/resources/maps/"
                            + maps.getClass()
                                    .getName()
                                    .replace("de.uol.swp.server.gamelogic.map.", "")
                            + ".map";

            if (new File(".").getAbsolutePath().endsWith("server\\.")) {
                path = path.replace("server/", "");
            }
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(path));
            objOut.writeObject(map);
            objOut.flush();
            objOut.close();
        }
    }

    /**
     * Copy the map
     *
     * @author Merden
     * @see de.uol.swp.server.gamelogic.Block
     * @since 2023-06-07
     */
    private static Block[][] copyMap(Block[][] originalMap) {
        Block[][] copy = new Block[originalMap.length][originalMap[0].length];
        for (int i = 0; i < originalMap.length; i++) {
            for (int j = 0; j < originalMap[0].length; j++) {
                copy[i][j] = originalMap[i][j].clone(); // Assuming Block class implements Cloneable
            }
        }
        return copy;
    }

    /**
     * Extract the map
     *
     * @author Merden
     * @see de.uol.swp.server.gamelogic.Block
     * @since 2023-06-07
     */
    private static Block[][] mapGenExtracted(
            Block[][] mapFromClass, int version, int checkpoints, int checkpointLoc) {
        int x = 0;
        int y = 0;
        Block[][] map = mapFromClass;
        // Place Checkpoints on normal maps (Version >0) and not on Test-maps with Version 0
        if (version >= 0) {
            try {
                int[][] checkpointLocation =
                        (int[][])
                                checkpointLocations
                                        .get(checkpointLoc)
                                        .get((version * 5) + checkpoints);

                for (int i = 0; i < checkpoints + 2; i++) {
                    x = checkpointLocation[0][i];
                    y = checkpointLocation[1][i];
                    map[x][y] =
                            new Block(
                                    new CheckPointBehaviour(null, map, new Position(x, y), (i + 1)),
                                    null,
                                    new Position(x, y));
                }
            } catch (Exception b) {
                LOG.warn("No Checkpoints found");
            }
        }

        // Place Laser and clear Behaviours that have Checkpoints in it
        int checkpointFound = 0;
        for (y = 0; y < 12; y++) {
            for (x = 0; x < 12; x++) {

                for (int i = 0; i < map[x][y].getBehaviourList().length; i++) {
                    if (map[x][y].getBehaviourList()[i] instanceof LaserBehaviour) {
                        if (((LaserBehaviour) map[x][y].getBehaviourList()[i]).getStart()) {
                            laserStart(
                                    map,
                                    x,
                                    y,
                                    ((LaserBehaviour) map[x][y].getBehaviourList()[i])
                                            .getLaserBeam(),
                                    ((LaserBehaviour) map[x][y].getBehaviourList()[i])
                                            .getLaserDirection());
                        }
                    } else if (map[x][y].getBehaviourList()[i] instanceof CheckPointBehaviour) {
                        checkpointFound++;
                    }
                }
            }
        }

        // Sort Behaviours
        for (y = 0; y < 12; y++) {
            for (x = 0; x < 12; x++) {
                for (int i = 0; i < map[x][y].getBehaviourList().length; i++) {
                    if (map[x][y].getBehaviourList()[i] instanceof CheckPointBehaviour) {
                        CheckPointBehaviour copy =
                                (CheckPointBehaviour) map[x][y].getBehaviourList()[i];
                        AbstractTileBehaviour[] emptyList = new AbstractTileBehaviour[1];
                        emptyList[0] = copy;
                        map[x][y] = new Block(emptyList, null, new Position(x, y));
                    }
                }
                map[x][y].setBehaviourList(sortBehaviourList(map[x][y].getBehaviourList()));
            }
        }
        return map;
    }
    /**
     * Set the start of the laser
     *
     * @author Merden
     * @see de.uol.swp.server.gamelogic.tiles.LaserBehaviour
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-06
     */
    private static void laserStart(
            Block[][] map, int x, int y, int beam, CardinalDirection direction) {
        int x2 = x;
        int y2 = y;

        boolean loop = true;
        CardinalDirection opposite = null;
        boolean innerWallOrLaser = false;
        boolean fullLaser = true;

        while (loop) {

            int pastX = x2;
            int pastY = y2;
            // Get next tile direction and sets opposite Cardinal Direction
            switch (direction) {
                case West:
                    x2--;
                    opposite = CardinalDirection.East;
                    break;
                case East:
                    x2++;
                    opposite = CardinalDirection.West;
                    break;
                case South:
                    y2++;
                    opposite = CardinalDirection.North;
                    break;
                case North:
                    y2--;
                    opposite = CardinalDirection.South;
                    break;
                default:
                    break;
            }
            // Check surrounding
            if (x2 >= 0 && y2 >= 0 && x2 <= 11 && y2 <= 11) {
                for (int i = 0; i < map[x2][y2].getBehaviourList().length; i++) {

                    // If the Block has a Wall it checks if the Wall blocks the Laser
                    // It then checks whether its an inner wall or an outer wall
                    if (map[x2][y2].getBehaviourList()[i] instanceof WallBehaviour) {
                        if (map[x2][y2].getBehaviourList()[i].getObstruction(opposite)) {
                            loop = false;
                            innerWallOrLaser = true;
                        } else if (map[x2][y2].getBehaviourList()[i].getObstruction(direction)) {
                            loop = false;
                        }
                    }
                    // Checks if the Tile has a Pusher which has the same direction, so then a half
                    // laser needs to be placed
                    if (map[x2][y2].getBehaviourList()[i] instanceof PusherBehaviour
                            && map[x2][y2].getBehaviourList()[i].getObstruction(direction)) {
                        fullLaser = false;
                        loop = false;
                    }
                    // If the Laser shoots at another startlaser then the startlaser gets replaced
                    if (map[x2][y2].getBehaviourList()[i] instanceof LaserBehaviour) {
                        if ((((LaserBehaviour) map[x2][y2].getBehaviourList()[i])
                                                .getLaserDirection()
                                        == direction)
                                || (((LaserBehaviour) map[x2][y2].getBehaviourList()[i])
                                                .getLaserDirection()
                                        == opposite)) {
                            if (((LaserBehaviour) map[x2][y2].getBehaviourList()[i]).getStart()) {
                                ((LaserBehaviour) map[x2][y2].getBehaviourList()[i])
                                        .setStart(false);
                                ((LaserBehaviour) map[x2][y2].getBehaviourList()[i])
                                        .setLaserBeam(beam);
                            }
                            innerWallOrLaser = true;
                        }
                    }
                    // If its a Checkpointbehavior a new Wall gets placed that blocks the Laser so
                    // the Laser doesn't touch the checkpoint

                    if (map[x2][y2].getBehaviourList()[i] instanceof CheckPointBehaviour) {
                        AbstractTileBehaviour[] copyForWall =
                                new AbstractTileBehaviour
                                        [map[pastX][pastY].getBehaviourList().length + 1];
                        System.arraycopy(
                                map[pastX][pastY].getBehaviourList(),
                                0,
                                copyForWall,
                                0,
                                copyForWall.length - 1);
                        copyForWall[copyForWall.length - 1] =
                                new WallBehaviour(null, map, new Position(pastX, pastY), direction);
                        innerWallOrLaser = true;
                        map[pastX][pastY] =
                                new Block(copyForWall, null, new Position(pastX, pastY));
                    }
                }

                // Place Laser if its eligible
                if (!innerWallOrLaser) {
                    AbstractTileBehaviour[] copyForLaser =
                            new AbstractTileBehaviour[map[x2][y2].getBehaviourList().length + 1];
                    System.arraycopy(
                            map[x2][y2].getBehaviourList(),
                            0,
                            copyForLaser,
                            0,
                            copyForLaser.length - 1);
                    copyForLaser[copyForLaser.length - 1] =
                            new LaserBehaviour(
                                    null,
                                    map,
                                    new int[] {1, 2, 3, 4, 5},
                                    new Position(x2, y2),
                                    direction,
                                    beam,
                                    fullLaser);
                    map[x2][y2] = new Block(copyForLaser, null, new Position(x2, y2));
                }
            }
            // If it goes out of the Map it should stop the loop
            else {
                loop = false;
            }
        }
    }

    /**
     * Sorts the behaviours so the pictures get selected correctly
     *
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-06-06
     */
    private static AbstractTileBehaviour[] sortBehaviourList(
            AbstractTileBehaviour[] behaviourList) {
        Arrays.sort(behaviourList, new BehaviourTypeComparator());

        AbstractTileBehaviour[] uniqueBehaviourList =
                new LinkedHashSet<>(Arrays.asList(behaviourList))
                        .toArray(new AbstractTileBehaviour[0]);

        return uniqueBehaviourList;
    }
}
