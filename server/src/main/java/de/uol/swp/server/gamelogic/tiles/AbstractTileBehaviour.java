package de.uol.swp.server.gamelogic.tiles;

import javax.swing.text.Position;

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
//    public boolean getWall(CardinalDirection){
        //TODO
//        return false;
//    }

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
    public de.uol.swp.server.gamelogic.Position getCheckPoint(){
        //TODO
        return 0;
    }

    /**
     * @author
     * @see
     * @since
     */
    public de.uol.swp.server.gamelogic.Position getCheckPointPosition(){
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
