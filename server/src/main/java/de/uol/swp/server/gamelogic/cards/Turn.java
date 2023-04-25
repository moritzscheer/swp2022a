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

    Direction directionCard;

    /**
     * Constructor
     *
     * @author Maria
     * @since 2023-04-23
     */
    public Turn(Direction dir) {
        this.directionCard = dir;
    }

    /**
     * Get Direction
     *
     * @author Maria
     * @since 2023-04-23
     */
    @Override
    public Direction getDirectionCard() {
        return this.directionCard;
    }

    /**
     * Depending on where the robot is looking and with card it is, changes the CardinalDirection of
     * the robot
     *
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    @Override
    public void turn(Robot robot) {
        if (this.directionCard.equals(Direction.Right)) {
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
        if (this.directionCard.equals(Direction.Left)) {
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
