package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * @author Tommy Dang
 * @since 2023-04-02
 */
abstract class CardBehaviour {

    private int moves;
    private Direction directionCard;
    private boolean uTurn;

    /**
     * The method is for the movement of the robot
     *
     * @author Tommy & WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-04-03
     */
    public void move(Robot robot, int moves) {}

    /**
      * The method is for the rotation of the robot
     *
     * @author Tommy & WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-04-03
     */
    public void turn(Robot robot) {}

    /**
     * The method is for the uTurn of the robot
     *
     * @author Tommy & WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-04-03
     */
    public void uTurn(Robot robot) {}

    /**
     * The method execute, execute the methods move, turn and uTurn
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-04-03
     */
    public void execute(Robot robot) {
        uTurn(robot);
        move(robot, moves);
        turn(robot);
    }

    /**
     * Get uTurn, overriden in uTurn
     *
     * @author Maria
     * @since 2023-04-23
     */
    public boolean getUTurn() {
        return false;
    }

    /**
     * Get direction, overriden in Turn
     *
     * @author Maria
     * @since 2023-04-23
     */
    public Direction getDirectionCard() {
        return null;
    }

    /**
     * Get moves, overriden in Straight
     *
     * @author Maria
     * @since 2023-04-23
     */
    public int getMoves() {
        return 0;
    }
}
