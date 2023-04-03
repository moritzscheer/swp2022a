package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * @author
 * @see
 * @since
 */
abstract class CardBehaviour {

    /**
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    public int move(Robot robot) {
        // TODO
        return 0;
    }

    /**
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    public Direction turn(Robot robot) {
        // TODO
        return null;
    }

    /**
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    public boolean uTurn(Robot robot) {
        // TODO
        return false;
    }

    /**
     * @author WKempel
     * @see
     * @since 2023-04-03
     */
    public void execute(Robot robot) {
        uTurn(robot);
        move(robot);
        turn(robot);
    }
}
