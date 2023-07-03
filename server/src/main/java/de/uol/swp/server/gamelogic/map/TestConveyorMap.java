package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import java.util.Random;

/**
 * @author Ole Zimmermann & Merden
 * @since 2023-03-05
 */
public class TestConveyorMap extends AbstractMap {

    /**
     * Constructor for TestConveyorMap
     *
     * @author Merden & Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.ConveyorBeltBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.ExpressConveyorBeltBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-11
     */
    public TestConveyorMap() {
        // ConveyorTest
        boolean express = false;

        for (int y = 1; y <= 11; y = y + 2) {
            for (int x = 0; x <= 11; x++) {

                if (y == 7) {
                    generateBlock(
                            map,
                            x + 6,
                            y,
                            new CheckPointBehaviour(null, map, new Position((x + 6), y), 1));
                    generateBlock(
                            map,
                            x + 8,
                            y,
                            new CheckPointBehaviour(null, map, new Position((x + 8), y), 3));
                    generateBlock(
                            map,
                            x + 10,
                            y,
                            new CheckPointBehaviour(null, map, new Position((x + 10), y), 2));
                    generateBlock(
                            map,
                            x + 11,
                            y,
                            new CheckPointBehaviour(null, map, new Position((x + 11), y), 4));
                    express = true;
                    y--;
                }

                Random random = new Random();

                int directionRDM = random.nextInt(4);
                CardinalDirection direction;
                int typeRDM = random.nextInt(21);

                ArrowType arrowType;

                switch (directionRDM) {
                    case 1:
                        direction = CardinalDirection.South;
                        break;
                    case 2:
                        direction = CardinalDirection.West;
                        break;
                    case 3:
                        direction = CardinalDirection.East;
                        break;
                    default:
                        direction = CardinalDirection.North;
                        break;
                }

                if (typeRDM < 10) {
                    arrowType = ArrowType.Straight;
                } else if (typeRDM < 14) {
                    arrowType = ArrowType.TurnRight;
                } else if (typeRDM < 18) {
                    arrowType = ArrowType.TurnLeft;
                } else if (typeRDM < 19) {
                    arrowType = ArrowType.StraightTurnRight;
                } else {
                    arrowType = ArrowType.StraightTurnLeft;
                }
                if (!express) {
                    generateBlock(
                            map,
                            x,
                            y,
                            new ConveyorBeltBehaviour(
                                    null, map, new Position(x, y), arrowType, direction));
                } else {
                    generateBlock(
                            map,
                            x,
                            y,
                            new ExpressConveyorBeltBehaviour(
                                    null, map, new Position(x, y), arrowType, direction));
                }
            }
        }
    }
}
