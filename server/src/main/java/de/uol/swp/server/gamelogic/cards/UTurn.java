package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 * Turns the robot 180 degree
 *
 * @author Tommy & WKempel
 * @see
 * @since 2023-04-03
 */
public class UTurn extends CardBehaviour {

    /**
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    @Override
    public void uTurn(Robot robot, boolean uTurn) {
        if (uTurn) {
            switch (robot.getDirection()) {
                case North:
                    robot.setDirection(CardinalDirection.South);
                    break;
                case East:
                    robot.setDirection(CardinalDirection.West);
                    break;
                case South:
                    robot.setDirection(CardinalDirection.North);
                    break;
                case West:
                    robot.setDirection(CardinalDirection.East);
                default:
                    break;
            }
        }
    }
}
