package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 *
 *
 * @author
 * @see
 * @since
 */

abstract class AbstractTileBehaviour {

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public CardinalDirection getWall(CardinalDirection direction){
        //TODO
        return direction;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public int getDamage(){
        //TODO
        return 0;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public int getRepair(){
        //TODO
        return 0;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public Position moveRobot(){
        //TODO
        return null;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public boolean killRobot(){
        //TODO
        return false;
    }

    /**
     * @author
     * @see
     * @since
     */
    public Position getCheckPoint(){
        //TODO
        return null;
    }

    /**
     * @author
     * @see
     * @since
     */
    public Position getCheckPointPosition(){
        //TODO
        return null;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public boolean turnRobot(){
        //TODO
        return false;
    }
}
