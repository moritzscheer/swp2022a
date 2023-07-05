package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tommy Dang & WKempel & Maria Andrade & Finn Oldeboershuis
 * @since 2023-03-05
 */
public abstract class AbstractTileBehaviour implements Serializable {

    protected List<Robot> robotStates;
    protected Block[][] board;
    protected Position blockPos;

    /**
     * @author Finn
     * @since 2023-03-05
     */
    public AbstractTileBehaviour(List<Robot> robotStates, Block[][] board, Position blockPos) {
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
     * @return List of planned but not necessarily possible moves
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> onRobotEntered(int indexOfMovedRobot) {
        return null;
    }

    /**
     * @return List of planned but not necessarily possible moves
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> onExpressConveyorStage(int programmStep) {
        return null;
    }

    /**
     * @return List of planned but not necessarily possible moves
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> onConveyorStage(int programmStep) {
        return null;
    }

    /**
     * @return List of planned but not necessarily possible moves
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> onLaserStage(int programmStep) {
        return null;
    }

    /**
     * @return List of planned but not necessarily possible moves
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> onPusherStage(int programmStep) {
        return null;
    }

    /**
     * @return new state of the game
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<MoveIntent> onPressorStage(int programmStep) {
        return null;
    }

    /**
     * @return ImagePath or Image Itself (maybe rotated)
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public List<int[]> getImage() {
        return new ArrayList<>();
    }

    /**
     * @return whether the move is blocked or not
     * @author Finn Oldeboershuis
     * @since 2023-01-29
     */
    public boolean getObstruction(CardinalDirection dir) {
        return false;
    }

    /**
     * return List of planned but not necessarily possible moves
     *
     * @author WKempel
     * @since 2023-03-22
     */
    public List<MoveIntent> onCardEnding(int programmStep) {
        return null;
    }

    /**
     * @author WKempel
     * @since 2023-03-05
     */
    public List<MoveIntent> onRotatorStage(int programStep) {
        return null;
    }

    /**
     * @author WKempel
     * @since 2023-03-05
     */
    public List<MoveIntent> onCheckPointStage(int programStep) {
        return null;
    }

    /**
     * @author Maria & Finn
     * @since 2023-03-05
     */
    public void setRobotStates(List<Robot> robots) {
        robotStates = robots;
    }

    /**
     * @author Maria
     * @since 2023-07-03
     */
    public void setBoard(Block[][] newBoard) {
        this.board = newBoard;
    }
}
