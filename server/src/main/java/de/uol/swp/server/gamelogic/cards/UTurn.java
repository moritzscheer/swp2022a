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
        if(uTurn == true){
            if(robot.getDirection().equals(CardinalDirection.North)){
                robot.setDirection(CardinalDirection.South);
            }
            if(robot.getDirection().equals(CardinalDirection.East)){
                robot.setDirection(CardinalDirection.West);
            }
            if(robot.getDirection().equals(CardinalDirection.South)){
                robot.setDirection(CardinalDirection.North);
            }
            if(robot.getDirection().equals(CardinalDirection.West)){
                robot.setDirection(CardinalDirection.East);
            }
        }

    }
}
