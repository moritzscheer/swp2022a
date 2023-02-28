package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 * @author
 * @see
 * @since
 */
public class WallBehaviour extends AbstractTileBehaviour {

    private CardinalDirection wallDirection;

    public WallBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public CardinalDirection getWall() {
        // TODO
        return wallDirection;
    }
}
