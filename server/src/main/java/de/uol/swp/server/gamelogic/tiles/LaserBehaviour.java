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
public class LaserBehaviour extends AbstractTileBehaviour {

    private CardinalDirection direction;
    private int damage;
    private int laserBeam;

    public LaserBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
        // TODO kmkm
    }

    /**
     * @author
     * @see
     * @since
     */
    public int getDamage() {
        // TODO
        return damage;
    }
}
