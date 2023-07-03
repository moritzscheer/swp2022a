package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

/**
 *
 *
 * @author
 * @since
 */
public class MapTwo extends AbstractMap{

    /**
     *
     *
     * @author
     * @since
     */
    public MapTwo(){
        int x = 0;
        int y = 0;
        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x,y), 1));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North));
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North));
        x = 5;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North));
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North));
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y);

        y = 1;

        x = 0;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x,y)));
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x,y), true));
        x = 11;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));

        y = 2;

        x = 0;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West));
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East), new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x,y), CardinalDirection.West, 1,  true, true));

        y = 3;

        x = 0;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 2;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 3;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x,y), false));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 10;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 11;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));

        y = 4;
        x = 0;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East),new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 5;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West),new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East));
        y = 5;
        x = 0;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 2;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 4;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 9;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 11;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));

        y = 6;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 2;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 4;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 8;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 9;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 10;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));
        x = 11;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));

        y = 7;

        x = 0;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East),new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North));
        x = 5;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West),new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North), new RepairBehaviour(null,map,new Position(x,y),2));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East));

        y = 8;

        x = 0;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 2;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 3;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x,y), false));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x,y), false));
        x = 9;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));
        x = 11;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.West));

        y = 9;

        x = 0;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.West));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East));

        y = 10;

        x = 0;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x,y)));
        x = 1;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 2;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.East));
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y, new GearBehaviour(null, map, new Position(x,y), true));
        x = 11;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.East));

        y = 11;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 3;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 4;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 5;
        generateBlock(map, x, y, new ExpressConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 8;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y, new WallBehaviour(null, map, new Position(x,y), CardinalDirection.South));
        x = 10;
        generateBlock(map, x, y, new ConveyorBeltBehaviour(null, map, new Position(x,y), ArrowType.Straight, CardinalDirection.North));
        x = 11;
        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x,y), 1));
    }
}
