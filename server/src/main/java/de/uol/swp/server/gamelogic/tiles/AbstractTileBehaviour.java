package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.List;

/**
 * @author
 * @see
 * @since
 */

public abstract class AbstractTileBehaviour {

    protected Robot[] robotStates;
    protected Block[][] board;
    protected Position blockPos;

    public AbstractTileBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        this.robotStates = robotStates;
        this.board = board;
        this.blockPos = blockPos;
    }

    /**
     * Getter for blockPos, mainly for tests purposes
     *
     * @return block position
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-05
     */
    public Position getBlockPos() {
        return this.blockPos;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> OnRobotEntered(int indexOfMovedRobot) {
        return null;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> OnExpressConveyorStage(int programmStep) {
        return null;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> OnConveyorStage(int programmStep) {
        return null;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> OnLaserStage(int programmStep) {
        return null;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> OnPusherStage(int programmStep) {
        return null;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> OnPresserStage(int programmStep) {
        return null;
    }

    /**
     * @return ImagePath or Image Itself (maybe rotated)
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public String GetImage() {
        return "";
    }

    /**
     * @param origin move origin
     * @param destination move destination
     * @return whether the move is blocked or not
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public boolean getObstruction(CardinalDirection dir) {
        return false;
    }
}
