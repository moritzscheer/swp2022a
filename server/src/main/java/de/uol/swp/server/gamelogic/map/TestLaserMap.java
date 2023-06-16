package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import java.util.Random;

public class TestLaserMap extends AbstractMap{

    public TestLaserMap(){
        // LASERTEST
        int x = 0;
        int y = 0;

        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.South, 1, true, true));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.South, 2, true, true));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.South, 3, true, true));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.South, 1, true, true));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.South, 2, true, true));
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.South, 3, true, true));
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
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.East, 1, true, true));
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
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.East, 2, true, true));
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
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.East, 3, true, true));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 11;
        generateBlock(map, x, y);

        y = 7;

        x = 0;
        generateBlock(map, x, y,
                new CheckPointBehaviour(null, map, new Position(x, y), 2));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y,
                new CheckPointBehaviour(null, map, new Position(x, y), 4));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y,
                new CheckPointBehaviour(null, map, new Position(x, y), 1));
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
        generateBlock(map, x, y,
                new CheckPointBehaviour(null, map, new Position(x, y), 3));

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
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.North, 3, true, true));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.North, 2, true, true));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.North, 1, true, true));
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.East, 3, true, true));
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y,
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x, y), CardinalDirection.West, 1, true, true));

        for(y = 1; y<11;y= y+3){
            for(x = 1; x<11;x= x+3){
                if (x > 11){
                    break;
                }
                    AbstractTileBehaviour[] copy = new AbstractTileBehaviour[map[x][y].getBehaviourList().length + 1];
                    System.arraycopy(map[x][y].getBehaviourList(), 0, copy, 0, copy.length - 1);
                    copy[copy.length - 1] = new RepairBehaviour(null,map,new Position(x,y),1);
                    map[x][y] = new Block(copy, null, new Position(x, y));
            }
        }


    }


}
