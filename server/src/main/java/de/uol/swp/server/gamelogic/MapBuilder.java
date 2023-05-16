package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.io.*;

/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @since 2023-04-28
 */
public final class MapBuilder {

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

        Block[][] map = getMap("server/src/main/resources/maps/tempMap.map");
        if (map != null) {
            System.out.println(map.length);
        }
    }

    public static void mapGen() throws IOException {
        Block[][] map = new Block[12][12];
        mapGenExtracted(map);

        ObjectOutputStream objOut =
                new ObjectOutputStream(new FileOutputStream("server/src/main/resources/maps/tempMap.map"));
        objOut.writeObject(map);
        objOut.flush();
        objOut.close();
    }

    private static void mapGenExtracted(Block[][] map) {
        int x = 0;
        int y = 0;

        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x, y), 0));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 3;
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
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
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
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 1;

        x = 0;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x, y), true));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnLeft,
                        CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[]{2, 4}, CardinalDirection.East));
        x = 6;
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
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 2;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South),
                new WallBehaviour(
                        null,
                        map,
                        new Position(x,y),
                        CardinalDirection.West));

        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y,
                new PitBehaviour(
                        null,
                        map,
                        new Position(x,y)));

        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[]{2, 4}, CardinalDirection.South));

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y,
                new PitBehaviour(
                        null,
                        map,
                        new Position(x,y)));


        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.North));

        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map,new Position(x,y),CardinalDirection.East));

        y = 3;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.East));
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnLeft,
                        CardinalDirection.South));

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
        generateBlock(map, x, y,new CheckPointBehaviour(null,map,new Position(x,y),2));
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.StraightTurnLeft,
                        CardinalDirection.North));

        x = 11;
        generateBlock(map, x, y);

        y = 4;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null,map, new int[] {1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));

        x = 1;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South),
                new LaserBehaviour(null, map,new int[]{1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));
        x = 2;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map,new int[]{1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));

        x = 3;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map,new int[]{1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));
        x = 4;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));

        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new CheckPointBehaviour(null,map,new Position(x,y),3));

        x = 8;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map,new int[]{1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));

        x = 9;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map,new int[]{1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));

        x = 10;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map,new int[]{1,2,3,4,5}, new Position(x,y),CardinalDirection.East, 1));

        x = 11;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new LaserBehaviour(null,map, new int[] {1,2,3,4,5}, new Position(x,y),CardinalDirection.West, 1));

        y = 5;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(
                null,
                map,
                new Position(x, y),
                ArrowType.Straight,
                CardinalDirection.South));
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null,map,new Position(x,y),CardinalDirection.East));

        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y,
                new RepairBehaviour(null, map, new Position(x, y), 0));

        x = 6;
        generateBlock(map, x, y,
                new RepairBehaviour(null, map, new Position(x, y), 0));

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[]{1,3,5}, CardinalDirection.West));

        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 6;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(
                null,
                map,
                new Position(x, y),
                ArrowType.Straight,
                CardinalDirection.South));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new PusherBehaviour(null, map, new Position(x, y), new int[]{1,3,5}, CardinalDirection.East));

        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y,
                new RepairBehaviour(null, map, new Position(x, y), 0));

        x = 6;
        generateBlock(map, x, y,
                new RepairBehaviour(null, map, new Position(x, y), 0));

        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new PusherBehaviour(null, map, new Position(x, y), new int[]{1,3,5}, CardinalDirection.West));

        x = 10;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.TurnRight, CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));


        y = 7;

        x = 0;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.East, 1));
        x = 1;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.East, 1));
        x = 2;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.East, 1));
        x = 3;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.East, 1));
        x = 4;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 8;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.West, 1));
        x = 9;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.West, 1));
        x = 10;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.West, 1));
        x = 11;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.West, 1));

        y = 8;

        x = 0;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y,
                new GearBehaviour(null, map, new Position(x, y), true));
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.North, 1 ));
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
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 9;

        x = 0;
        generateBlock(map, x, y,
                new WallBehaviour(null,map, new Position(x,y),CardinalDirection.West));

        x = 1;
        generateBlock(map, x, y, new CheckPointBehaviour(null,map,new Position(x,y),4));
        x = 2;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map, new int[]{1,2,3,4,5}, new Position(x, y), CardinalDirection.North, 1 ));
        x = 3;
        generateBlock(map, x, y,
                new PitBehaviour(null,map, new Position(x,y)));

        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y,new WallBehaviour(null,map, new Position(x,y),CardinalDirection.West));
        x = 6;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new PusherBehaviour(null, map, new Position(x, y), new int[]{3}, CardinalDirection.North));


        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y,
                new PitBehaviour(null,map, new Position(x,y)));

        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));

        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

        y = 10;

        x = 0;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.Straight,CardinalDirection.East));
        x = 1;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.StraightTurnRight,CardinalDirection.South));
        x = 2;
        generateBlock(map, x, y,new LaserBehaviour(null,map,new int[]{1,2,3,4,5},new Position(x,y),CardinalDirection.South,1));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.StraightTurnRight,CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.Straight,CardinalDirection.East));
        x = 8;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.Straight,CardinalDirection.East));
        x = 9;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.Straight,CardinalDirection.East));
        x = 10;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.StraightTurnLeft,CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 11;

        x = 0;
        generateBlock(map, x, y, new CheckPointBehaviour(null,map,new Position(x,y),1));
        x = 1;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.Straight,CardinalDirection.South));
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null,map,new Position(x,y),CardinalDirection.South),
                new LaserBehaviour(null,map,new int[]{1,2,3,4,5},new Position(x,y),CardinalDirection.South,1));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y,new WallBehaviour(null,map,new Position(x,y),CardinalDirection.South));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y,new ConveyorBeltBehaviour(null,map,new Position(x,y),ArrowType.Straight,CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y,new WallBehaviour(null,map,new Position(x,y),CardinalDirection.South));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y,new WallBehaviour(null,map,new Position(x,y),CardinalDirection.South));
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y,new RepairBehaviour(null,map,new Position(x,y),1));
    }

    private static void generateBlock(
            Block[][] map, int x, int y, AbstractTileBehaviour... behaviours) {
        map[x][y] = new Block(behaviours, null, new Position(x, y));
    }
}
