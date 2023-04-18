package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 * Turns the robot 90 degree in clockwise or counterclockwise
 *
 * @author Tommy & WKempel
 * @see
 * @since 2023-04-03
 */
public class Turn extends CardBehaviour {

    /**
     * Depending on where the robot is looking and with card it is, changes the CardinalDirection of
     * the robot
     *
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    @Override
    public void turn(Robot robot, Direction directionCard) {
        if (directionCard.equals(Direction.Right)) {
            switch (robot.getDirection()) {
                case North:
                    robot.setDirection(CardinalDirection.East);
                    break;
                case East:
                    robot.setDirection(CardinalDirection.South);
                    break;
                case South:
                    robot.setDirection(CardinalDirection.West);
                    break;
                case West:
                    robot.setDirection(CardinalDirection.North);
                    break;
                default:
                    break;
            }
        }
        if (directionCard.equals(Direction.Left)) {
            switch (robot.getDirection()) {
                case North:
                    robot.setDirection(CardinalDirection.West);
                    break;
                case West:
                    robot.setDirection(CardinalDirection.South);
                    break;
                case South:
                    robot.setDirection(CardinalDirection.East);
                    break;
                case East:
                    robot.setDirection(CardinalDirection.North);
                    break;
                default:
                    break;
            }
        }
    }
}
