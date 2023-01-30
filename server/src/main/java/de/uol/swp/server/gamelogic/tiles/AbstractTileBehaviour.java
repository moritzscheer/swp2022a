package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Position;
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
     * @param currentStates current state of the game
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public Robot[] OnRobotMoved(Robot[] currentStates, int indexOfMovedRobot){
        return currentStates;
    }

    /**
     * @param currentStates current state of the game
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public Robot[] OnExpressConveyorStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates current state of the game
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public Robot[] OnConveyorStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates current state of the game
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public Robot[] OnLaserStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates current state of the game
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public Robot[] OnPusherStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @param currentStates current state of the game
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public Robot[] OnPresserStage(Robot[] currentStates){
        return currentStates;
    }

    /**
     * @return ImagePath or Image Itself (maybe rotated)
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public String GetImage(){
        return "";
    }


    /**
     * @param origin move origin
     * @param destination move destination
     * @return whether the move is blocked or not
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public boolean getObstruction(Position origin, Position destination){
        return false;
    }
}
