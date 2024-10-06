package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Robot;

/**
 * Turns the robot 180 degree
 *
 * @author Tommy & WKempel
 * @since 2023-04-03
 */
public class UTurn extends CardBehaviour {

    private boolean uTurn;

    /**
     * Constructor
     *
     * @author Maria
     * @since 2023-04-23
     */
    public UTurn() {
        this.uTurn = true;
    }

    /**
     * getUTurn
     *
     * @author Maria
     * @since 2023-04-23
     */
    @Override
    public boolean getUTurn() {
        return this.uTurn;
    }

    /**
     * method to turn the robot 180 degree
     *
     * @author Tommy & WKempel
     * @since 2023-04-03
     */
    @Override
    public void uTurn(Robot robot) {
        if (this.uTurn) {
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
