package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

/**
 * @author
 * @see
 * @since
 */
public class RepairBehaviour extends AbstractTileBehaviour {

    private int repairSiteKey;

    public RepairBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
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
}
