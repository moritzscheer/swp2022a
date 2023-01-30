package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 *
 *
 * @author
 * @see
 * @since
 */
public class LaserBehaviour extends AbstractTileBehaviour{

    private CardinalDirection direction;
    private int damage;
    private int laserBeam;

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public int getDamage() {
        //TODO
        return damage;
    }
}
