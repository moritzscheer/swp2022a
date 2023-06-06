package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import java.io.*;

/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @since 2023-04-28
 */
public final class MapBuilderTESTMAP {

    public static Block[][] getMap(String mapPath) {
        ObjectInputStream objIn = null;
        try {
            objIn = new ObjectInputStream(new FileInputStream(mapPath));
            Block[][] map = (Block[][]) objIn.readObject();
            objIn.close();
            return map;
        } catch (IOException | ClassNotFoundException IOExcept) {
            System.out.println(IOExcept.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        mapGen();

        Block[][] map = getMap("server/src/main/resources/maps/testMap.map");
        if (map != null) {
            System.out.println(map.length);
        }
    }

    public static void mapGen() throws IOException {
        Block[][] map = new Block[12][12];
        mapGenExtracted(map);

        ObjectOutputStream objOut =
                new ObjectOutputStream(
                        new FileOutputStream("server/src/main/resources/maps/testMap.map"));
        objOut.writeObject(map);
        objOut.flush();
        objOut.close();
    }

    private static void mapGenExtracted(Block[][] map) {

        // SECOND TESTMAP
        int x = 0;
        int y = 0;

        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East),
                new PressorBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {3},
                        CardinalDirection.East,
                        true));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East),
                new PressorBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {2, 4},
                        CardinalDirection.East,
                        true));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East),
                new PressorBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {2, 4},
                        CardinalDirection.East,
                        false));
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East),
                new PressorBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {1, 5},
                        CardinalDirection.East,
                        false));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 1;

        x = 0;
        generateBlock(map, x, y);

        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 2;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);

        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);

        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);

        x = 11;
        generateBlock(map, x, y);

        y = 3;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        3,
                        true,
                        true));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        3,
                        false,
                        true));

        x = 2;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        3,
                        false,
                        true));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        3,
                        false,
                        true),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);

        x = 11;
        generateBlock(map, x, y);

        y = 4;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        2,
                        true,
                        true));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        2,
                        false,
                        true));

        x = 2;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        2,
                        false,
                        true));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        2,
                        false,
                        true),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));
        x = 4;
        generateBlock(map, x, y);

        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);

        x = 8;
        generateBlock(map, x, y);

        x = 9;
        generateBlock(map, x, y);

        x = 10;
        generateBlock(map, x, y);

        x = 11;
        generateBlock(map, x, y);

        y = 5;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        1,
                        true,
                        true));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        1,
                        false,
                        true));

        x = 2;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        1,
                        false,
                        true));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.East,
                        1,
                        false,
                        true),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);

        x = 6;
        generateBlock(map, x, y);

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);

        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 6;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.West,
                        2,
                        true,
                        true));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);

        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);

        x = 6;
        generateBlock(map, x, y);

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);

        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 7;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.West,
                        1,
                        true,
                        true));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 8;

        x = 0;
        generateBlock(map, x, y, new CheckPointBehaviour(null, map, new Position(x, y), 3));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 10;
        generateBlock(map, x, y);

        x = 11;
        generateBlock(map, x, y);

        y = 9;

        x = 0;
        generateBlock(map, x, y, new CheckPointBehaviour(null, map, new Position(x, y), 2));

        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);

        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);

        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));

        x = 11;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));

        y = 10;

        x = 0;
        generateBlock(map, x, y, new CheckPointBehaviour(null, map, new Position(x, y), 1));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x, y), false));
        x = 4;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x, y), true));
        x = 5;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x, y)));
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.North));
        x = 10;
        generateBlock(map, x, y);

        x = 11;
        generateBlock(map, x, y);

        y = 11;

        x = 0;
        generateBlock(map, x, y, new CheckPointBehaviour(null, map, new Position(x, y), 0));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {1}, CardinalDirection.South));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2}, CardinalDirection.South));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {3}, CardinalDirection.South));
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.South));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new PusherBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {1, 3, 5},
                        CardinalDirection.South));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.North));
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));

        x = 11;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
    }

    private static void generateBlock(
            Block[][] map, int x, int y, AbstractTileBehaviour... behaviours) {
        map[x][y] = new Block(behaviours, null, new Position(x, y));
    }
}
