package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public final class MapBuilder {

    public static Block[][] getMap(String xmlPath) {
        ObjectInputStream objIn = null;
        try {
            objIn = new ObjectInputStream(new FileInputStream(xmlPath));
            Block[][] map = (Block[][]) objIn.readObject();
            objIn.close();
            return map;
        } catch (IOException | ClassNotFoundException IOExcept) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        MapGen();

        Block[][] map = getMap("C:\\\\Temp\\\\tempMap.map");
        if (map != null) {
            System.out.println(map.length);
        }
    }

    public static void MapGen() throws IOException {
        Block[][] map = new Block[12][12];
        MapGenExtracted(map);

        ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("C:\\Temp\\tempMap.map"));
        objOut.writeObject(map);
        objOut.flush();
        objOut.close();
    }

    private static void MapGenExtracted(Block[][] map) {
        int x = 0;
        int y = 0;

        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x, y), 0));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 3;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 1;

        x = 0;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x, y), true));
        x = 1;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 2;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 3;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.TurnLeft, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West), new PusherBehaviour(null, map, new Position(x, y), new int[]{2, 4}, CardinalDirection.East));
        x = 6;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
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

        y = 4;

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

        y = 5;

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

        y = 6;

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

        y = 7;

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

        y = 8;

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

        y = 9;

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

        y = 10;

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

        y = 11;

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
    }

    private static void generateBlock(Block[][] map, int x, int y, AbstractTileBehaviour... behaviours) {
        AbstractTileBehaviour[] beh = behaviours;
        map[x][y] = new Block(beh, null, new Position(x, y));
    }

}
