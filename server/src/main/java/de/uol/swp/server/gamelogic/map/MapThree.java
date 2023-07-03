package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

/**
 * This is the third map of the game
 *
 * @author Ole Zimmermann & Merden
 * @since 2023-06-26
 */
public class MapThree extends AbstractMap {

    /**
     * Constructor
     *
     * @author Merden & ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.WallBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.ConveyorBeltBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.PusherBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Block
     * @see de.uol.swp.common.game.Position
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.enums.ArrowType
     * @since 2023-06.26
     */
    public MapThree() {
        int x = 0;
        int y = 0;

        generateBlock(map, x, y);
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
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {1, 3, 5},
                        CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 5;
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
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {1, 3, 5},
                        CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x, y), 0));

        y = 1;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.TTurn, CardinalDirection.East));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 5;
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
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 9;
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
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnLeft,
                        CardinalDirection.South));
        x = 11;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));

        y = 2;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West));

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
                        CardinalDirection.North));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.East));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.South));
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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.East),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

        y = 3;

        x = 0;
        generateBlock(map, x, y);
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
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
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
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 7;
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
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(map, x, y);

        y = 4;
        x = 0;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {1, 3, 5}, CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West));
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
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
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
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.East));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.East));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.South),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.North));
        x = 7;
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
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {1, 3, 5}, CardinalDirection.East),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));
        y = 5;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
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
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
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
        x = 4;
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
        x = 5;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x, y)));
        x = 6;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x, y)));
        x = 7;
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
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.StraightTurnLeft,
                        CardinalDirection.South));
        x = 11;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));

        y = 6;

        x = 0;
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
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.StraightTurnLeft,
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West));
        x = 4;
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
        x = 5;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x, y)));
        x = 6;
        generateBlock(map, x, y, new PitBehaviour(null, map, new Position(x, y)));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.South),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.West,
                        1,
                        true,
                        true));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));

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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(map, x, y);

        y = 7;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {1, 3, 5}, CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West));
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
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
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
        x = 4;
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
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.North),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.North,
                        1,
                        true,
                        true));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.West));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {1, 3, 5}, CardinalDirection.East),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

        y = 8;

        x = 0;
        generateBlock(map, x, y);
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
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
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
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.North));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South),
                new LaserBehaviour(
                        null,
                        map,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(x, y),
                        CardinalDirection.North,
                        1,
                        true,
                        true));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.West));
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
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(map, x, y);

        y = 9;

        x = 0;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.West),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.West));
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
                        CardinalDirection.North));
        x = 2;
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
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.North));
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new ConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.West));
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.Straight,
                        CardinalDirection.South));
        x = 11;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.East),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.East));

        y = 10;

        x = 0;
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
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnLeft,
                        CardinalDirection.North));
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.TurnRight,
                        CardinalDirection.North));
        x = 3;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 5;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 6;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        ArrowType.StraightTurnLeft,
                        CardinalDirection.West));
        x = 7;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 8;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.Straight, CardinalDirection.West));
        x = 10;
        generateBlock(
                map,
                x,
                y,
                new ExpressConveyorBeltBehaviour(
                        null, map, new Position(x, y), ArrowType.TTurn, CardinalDirection.West));
        x = 11;
        generateBlock(map, x, y);

        y = 11;

        x = 0;
        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x, y), 1));
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.South),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {1, 3, 5},
                        CardinalDirection.South),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 5;
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
                new PusherBehaviour(
                        null,
                        map,
                        new Position(x, y),
                        new int[] {1, 3, 5},
                        CardinalDirection.South),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 8;
        generateBlock(map, x, y, new RepairBehaviour(null, map, new Position(x, y), 2));
        x = 9;
        generateBlock(
                map,
                x,
                y,
                new PusherBehaviour(
                        null, map, new Position(x, y), new int[] {2, 4}, CardinalDirection.South),
                new WallBehaviour(null, map, new Position(x, y), CardinalDirection.South));
        x = 10;
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
        x = 11;
        generateBlock(map, x, y);
    }
}
