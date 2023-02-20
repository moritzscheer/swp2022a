package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;

import javax.swing.text.Position;

/**
 * @author
 * @see
 * @since
 */
public class PusherBehaviour extends AbstractTileBehaviour {

    private int[] activeInProgramSteps;

    public PusherBehaviour(
            Robot[] robotStates, Block[][] board, de.uol.swp.server.gamelogic.Position blockPos) {
        super(robotStates, board, blockPos);
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    private Position moveRobot(int programStep) {
        // TODO
        return null;
    }
}
