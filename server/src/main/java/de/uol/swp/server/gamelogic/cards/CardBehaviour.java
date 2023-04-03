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
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    public void move(Robot robot, int moves) {
    }

    /**
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    public void turn(Robot robot, Direction directionCard) {
    }

    /**
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    public void uTurn(Robot robot, boolean uTurn) {
    }

    /**
     * @author WKempel
     * @see
     * @since 2023-04-03
     */
    public void execute(Robot robot) {
        uTurn(robot, uTurn);
        move(robot, moves);
        turn(robot, directionCard);
    }
}
