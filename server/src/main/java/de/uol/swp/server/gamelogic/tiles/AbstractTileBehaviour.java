package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Robot;

/**
 *
 *
 * @author
 * @see
 * @since
 */

abstract class AbstractTileBehaviour {

//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public CardinalDirection getWall(CardinalDirection direction) {
//        //TODO
//        return direction;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public int getDamage() {
//        //TODO
//        return 0;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public int getRepair() {
//        //TODO
//        return 0;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public Position moveRobot() {
//        //TODO
//        return null;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public boolean killRobot() {
//        //TODO
//        return false;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public Position getCheckPoint() {
//        //TODO
//        return null;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public Position getCheckPointPosition() {
//        //TODO
//        return null;
//    }
//
//    /**
//     * @author
//     * @see
//     * @since
//     */
//    public boolean turnRobot() {
//        //TODO
//        return false;
//    }

    /**
     * @param currentStates
     * @return
     * @author
     * @since
     */
    public Robot[] OnRobotMoved(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates
     * @return
     * @author
     * @since
     */
    public Robot[] OnExpressConveyorStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates
     * @return
     * @author
     * @since
     */
    public Robot[] OnConveyorStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates
     * @return
     * @author
     * @since
     */
    public Robot[] OnLaserStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates
     * @return
     * @author
     * @since
     */
    public Robot[] OnPusherStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates
     * @return
     * @author
     * @since
     */
    public Robot[] OnPresserStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @return ImagePath or Image Itself (maybe rotatet)
     * @author
     * @since
     */
    public String GetImage(){
        return "";
    }
}
