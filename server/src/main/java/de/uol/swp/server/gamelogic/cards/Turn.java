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
     * Depending on where the robot is looking and with card it is, changes the CardinalDirection of the robot
     *
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    @Override
    public void turn(Robot robot, Direction directionCard) {
        if(robot.getDirection().equals(CardinalDirection.North)){
            if(directionCard.equals(Direction.Right)){
                robot.setDirection(CardinalDirection.East);
            }
            else {
                robot.setDirection(CardinalDirection.West);
            }
        }

        if(robot.getDirection().equals(CardinalDirection.East)){
            if(directionCard.equals(Direction.Right)){
                robot.setDirection(CardinalDirection.South);
            }
            else {
                robot.setDirection(CardinalDirection.North);
            }
        }

        if(robot.getDirection().equals(CardinalDirection.South)){
            if(directionCard.equals(Direction.Right)){
                robot.setDirection(CardinalDirection.West);
            }
            else {
                robot.setDirection(CardinalDirection.East);
            }
        }

        if(robot.getDirection().equals(CardinalDirection.West)){
            if(directionCard.equals(Direction.Right)){
                robot.setDirection(CardinalDirection.North);
            }
            else {
                robot.setDirection(CardinalDirection.South);
            }
        }
    }
}
