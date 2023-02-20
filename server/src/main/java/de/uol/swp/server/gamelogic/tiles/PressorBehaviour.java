package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

/**
 * @author
 * @see
 * @since
 */
public class PressorBehaviour extends AbstractTileBehaviour {

    private int[] activeInProgramSteps;

    public PressorBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    private boolean killRobot(int programStep) {
        // TODO
        return false;
    }
}
