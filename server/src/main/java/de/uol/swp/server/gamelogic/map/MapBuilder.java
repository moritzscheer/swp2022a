package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.BehaviourTypeComparator;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.tiles.*;

import org.javatuples.Pair;

import java.io.*;
import java.util.*;

/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @since 2023-04-28
 */
public final class MapBuilder {

    public static List<AbstractMap> maps = new LinkedList<AbstractMap>();
    public static List<AbstractMap> testMaps = new LinkedList<AbstractMap>();
    private static final HashMap<String, Pair<Integer, Position>>
            mapStringToCheckpointNumberAndFirstPosition = new HashMap<>();

    public static List<ArrayList> checkpointLocations = new LinkedList<ArrayList>();
    public static ArrayList<int[][]> checkpointsMapOne = new ArrayList<>();

    static {
        // define all three positions for each version
        // Map1
        Position[] versionPositionsMap1 = {
            new Position(4,11), new Position(4, 3), new Position(3, 5)
        };
        for (int v = 1; v <= 3; v++) {
            for (int c = 2; c <= 6; c++) {
                mapStringToCheckpointNumberAndFirstPosition.put(
                        "MapOneV" + (v) + "C" + (c), new Pair<>(c, versionPositionsMap1[v - 1]));
            }
        }

        // Map2

        // Map3

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
     *
     *
     * @author
     * @since
     */
    public static Block[][] getMap(String mapPath) {
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(mapPath));
            Block[][] map = (Block[][]) objIn.readObject();
            objIn.close();
            return map;
        } catch (IOException | ClassNotFoundException IOExcept) {
            try {
                ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(mapPath.replace("server/", "")));
                Block[][] map = (Block[][]) objIn.readObject();
                objIn.close();
                return map;
            }catch (IOException | ClassNotFoundException IOExcept2){
                System.out.println(IOExcept2.getMessage());
                return null;
            }
        }
    }

    /**
     *
     *
     * @author
     * @since
     */
    public static void main(String[] args) throws IOException {

        maps.add(new MapOne());
        maps.add(new MapTwo());
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

        mapGen();

        // Block[][] map = getMap("server/src/main/resources/maps/MapOneV1C2.map");
        // if (map != null) {
        //     System.out.println(map.length);
        // }
    }

    /**
     *
     *
     * @author
     * @since
     */
    public static Pair<Integer, Position> getMapStringToCheckpointNumberAndFirstPosition(
            String mapName) {
        System.out.println("In getMapStringToCheckpointNumberAndFirstPosition");
        System.out.println("gettting info for " + mapName);
        System.out.println(
                "---------> " + mapStringToCheckpointNumberAndFirstPosition.get(mapName));
        return mapStringToCheckpointNumberAndFirstPosition.get(mapName);
    }

    /**
     *
     *
     * @author
     * @since
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

                    if(new File(".").getAbsolutePath().endsWith("server\\.")){
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

            if(new File(".").getAbsolutePath().endsWith("server\\.")){
                path = path.replace("server/", "");
            }

            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(path));
            objOut.writeObject(map);
            objOut.flush();
            objOut.close();
        }
    }

    /**
     *
     *
     * @author
     * @since
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
     *
     *
     * @author
     * @since
     */
    private static Block[][] mapGenExtracted(
            Block[][] mapFromClass, int version, int checkpoints, int checkpointLoc) {
        int x = 0;
        int y = 0;
        Block[][] map = mapFromClass;

        // Place Checkpoints on normal maps (Version >0) and not on Testmaps with Version 0
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
                    System.out.println(x + " " + y);
                    map[x][y] =
                            new Block(
                                    new CheckPointBehaviour(null, map, new Position(x, y), (i + 1)),
                                    null,
                                    new Position(x, y));
                }
                System.out.println("NEXT");

            } catch (Exception b) {
                System.out.println("No Checkpoints Found!");
            }
        }

        // Place Laser and clear Behaviours that have Checkpoints in it
        int checkpointFound = 0;
        for (y = 0; y < 12; y++) {
            for (x = 0; x < 12; x++) {

                // boolean pitBehaviourFound = false;
                // boolean conveyorBeltBehaviourFound = false;
                // boolean repairBehaviourFound = false;
                // boolean gearBehaviourFound = false;
                for (int i = 0; i < map[x][y].getBehaviourList().length; i++) {
                    if (map[x][y].getBehaviourList()[i] instanceof LaserBehaviour) {
                        if (((LaserBehaviour) map[x][y].getBehaviourList()[i]).getStart() == true) {
                            laserStart(
                                    map,
                                    x,
                                    y,
                                    ((LaserBehaviour) map[x][y].getBehaviourList()[i])
                                            .getLaserBeam(),
                                    ((LaserBehaviour) map[x][y].getBehaviourList()[i])
                                            .getLaserDirection());
                        }
                    }
                    /*
                    else if(map[x][y].getBehaviourList()[i] instanceof PitBehaviour){
                        pitBehaviourFound = true;
                    }
                    */
                    else if (map[x][y].getBehaviourList()[i] instanceof CheckPointBehaviour) {
                        checkpointFound++;
                    }
                    /*
                    else if(map[x][y].getBehaviourList()[i] instanceof ConveyorBeltBehaviour || map[x][y].getBehaviourList()[i] instanceof ExpressConveyorBeltBehaviour){
                        conveyorBeltBehaviourFound = true;
                    }
                    else if(map[x][y].getBehaviourList()[i] instanceof RepairBehaviour){
                        repairBehaviourFound = true;
                    }
                    else if(map[x][y].getBehaviourList()[i] instanceof GearBehaviour){
                        gearBehaviourFound = true;
                     */
                }
            }
        }
        System.out.println("Checkpoints: " + checkpointFound);

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
     *
     *
     * @author
     * @since
     */
    private static void laserStart(
            Block[][] map, int x, int y, int beam, CardinalDirection direction) {
        int x2 = x;
        int y2 = y;

        boolean loop = true;
        CardinalDirection opposite = null;
        boolean innerWallorLaser = false;
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
                            innerWallorLaser = true;
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
                            innerWallorLaser = true;
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
                        innerWallorLaser = true;
                        map[pastX][pastY] =
                                new Block(copyForWall, null, new Position(pastX, pastY));
                    }
                }

                // Place Laser if its eligible
                if (!innerWallorLaser) {
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
     *
     *
     * @author
     * @since
     */
    // Sorts the behaviours so the pictures get selected correctly
    private static AbstractTileBehaviour[] sortBehaviourList(
            AbstractTileBehaviour[] behaviourList) {
        Arrays.sort(behaviourList, new BehaviourTypeComparator());

        AbstractTileBehaviour[] uniqueBehaviourList =
                new LinkedHashSet<>(Arrays.asList(behaviourList))
                        .toArray(new AbstractTileBehaviour[0]);

        return uniqueBehaviourList;
    }
}
