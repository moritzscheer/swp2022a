package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * Moves the robot one to three tiles forward in direction where the robot is looking
 *
 * @author
 * @see
 * @since 2023-04-03
 */
public class Straight extends CardBehaviour {

    private int moves;

    /**
     * Constructor
     *
     * @author Maria
     * @since 2023-04-23
     */
    public Straight(int moves) {
        this.moves = moves;
    }

    /**
     * Get how many moves
     *
     * @author Maria
     * @since 2023-04-23
     */
    @Override
    public int getMoves() {
        return this.moves;
    }

    /**
     * Moves the robot forward in the direction where the robot is looking. With the parameter moves
     * tells the roboter how many tiles it goes.
     *
     * @author Tommy & WKempel
     * @see
     * @since 2023-04-03
     */
    @Override
    public void move(Robot robot, int moves) {
        for (int i = 1; i <= moves; i++) {
            robot.move(robot.getDirection());
        }
    }
}
