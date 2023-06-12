package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

public class MapOne extends AbstractMap{

    public MapOne(){
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
        generateBlock(
                map,
                x,
                y,
                new GearBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        true));
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
                        null, map, new Position(x, y), new int[]{2, 4}, CardinalDirection.West));
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
                        new Position(x, y),
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
                        new Position(x, y)));

        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
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
                        new Position(x, y)));


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
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

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
                        ArrowType.StraightTurnLeft,
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
                        ArrowType.TurnRight,
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

        y = 4;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.East, 1, true, true));

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
                        CardinalDirection.South));

        x = 2;
        generateBlock(map, x, y);


        x = 3;
        generateBlock(map, x, y,
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.West, 1, true, true),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

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
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));


        x = 8;
        generateBlock(map, x, y);


        x = 9;
        generateBlock(map, x, y);


        x = 10;
        generateBlock(map, x, y);


        x = 11;
        generateBlock(
                map,
                x,
                y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.West, 1, true, true));

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
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

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
                        null, map, new Position(x, y), new int[]{1, 3, 5}, CardinalDirection.West));

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
                new PusherBehaviour(null, map, new Position(x, y), new int[]{1, 3, 5}, CardinalDirection.East));

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
                new PusherBehaviour(null, map, new Position(x, y), new int[]{1, 3, 5}, CardinalDirection.West));

        x = 10;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.TurnRight, CardinalDirection.East));
        x = 11;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));


        y = 7;

        x = 0;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.East, 1, true, true));
        x = 1;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.South));

        x = 2;
        generateBlock(map, x, y);

        x = 3;
        generateBlock(map, x, y);

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
        generateBlock(map, x, y);

        x = 9;
        generateBlock(map, x, y);

        x = 10;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));

        x = 11;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.West, 1, true, true));

        y = 8;

        x = 0;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y,
                new GearBehaviour(null, map, new Position(x, y), false));
        x = 2;
        generateBlock(map, x, y

                ,new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North)
        );
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
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West));

        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);


        x = 3;
        generateBlock(map, x, y,
                new PitBehaviour(null, map, new Position(x, y)));

        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 6;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new PusherBehaviour(null, map, new Position(x, y), new int[]{3}, CardinalDirection.North));


        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y,
                new PitBehaviour(null, map, new Position(x, y)));

        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y,
                new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));

        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

        y = 10;

        x = 0;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.TurnRight, CardinalDirection.South));
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.TurnRight, CardinalDirection.East));
        x = 7;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 9;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.TurnLeft, CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 11;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.South));
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.North, 1, true, true));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x, y), 1));
    }
}
