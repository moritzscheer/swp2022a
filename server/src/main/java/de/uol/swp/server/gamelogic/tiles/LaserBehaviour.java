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
    private int laserBeam; // laserBeam is directed related to damage

    public LaserBehaviour(
            Robot[] robotStates,
            Block[][] board,
            Position blockPos,
            CardinalDirection laserDir,
            int laserBeam) {
        super(robotStates, board, blockPos);
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public int damageRobot() {
        // TODO
        return laserBeam;
    }

    /**
     * Getter for test
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-13
     */
    public CardinalDirection getLaserDirection() {
        return this.direction;
    }
    /**
     * Getter for test
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-13
     */
    public int getLaserBeam() {
        return this.laserBeam;
    }
}
