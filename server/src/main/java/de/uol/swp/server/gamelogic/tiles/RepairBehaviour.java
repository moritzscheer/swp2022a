package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.Objects;

/**
 * @author Maria Eduarda Costa Leite Andrade & WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 2023-03-13
 */
public class RepairBehaviour extends AbstractTileBehaviour {

    // (1)single-wrench: discard 1 Damage token
    // (2)crossed wrench/hammer: discard 1 Damage token AND draw one Option card.
    private int repairSiteKey; // 1 or 2


    public RepairBehaviour(
            Robot[] robotStates, Block[][] board, Position blockPos, int repairSiteKey) {
        super(robotStates, board, blockPos);
        this.repairSiteKey = repairSiteKey;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-02-26
     */
    public Position getCheckPointPosition() {
        return this.blockPos;
    }

    /**
     * @author Wkempel
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @since 2023-03-13
     */
    public void repairDamage() {
        for(Robot robotState : robotStates) {
            if(Objects.equals(robotState.getPosition(),blockPos)) {
                robotState.setDamageToken(robotState.getDamageToken() - repairSiteKey);
                robotState.setLastCheckPointPosition(blockPos);

            }
        }
    }
}
