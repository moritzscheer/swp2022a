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

    // (1)single-wrench: discard 1 Damage token
    // (2)crossed wrench/hammer: discard 1 Damage token AND draw one Option card.
    private int repairSiteKey; // 1 or 2

    public RepairBehaviour(
            Robot[] robotStates, Block[][] board, Position blockPos, int repairSiteKey) {
        super(robotStates, board, blockPos);
        // TODO
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-02-26
     */
    public Position getCheckPoint() {
        return this.blockPos;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void setCheckPoint() {
        // TODO
        // set as check point for robot
    }

    /**
     * @author
     * @see
     * @since
     */
    public void repairDamage() {
        // TODO
    }
}
