package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

/**
 * @author
 * @see
 * @since
 */
public class CheckPointBehaviour extends AbstractTileBehaviour {

    private int number;

    public CheckPointBehaviour(
            Robot[] robotStates, Block[][] board, Position blockPos, int checkPointNumber) {
        super(robotStates, board, blockPos);
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public Position getCheckPoint() {
        // TODO
        return null;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void setCheckPoint() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public Position getCheckPointPosition() {
        // TODO
        return null;
    }
}
