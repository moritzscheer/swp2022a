package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.List;

/**
 * Checks, if a robot is on the checkpoint tile and changes to the new one, if needed
 *
 * @author Tommy Dang
 * @since 28.02.2023
 */
public class CheckPointBehaviour extends AbstractTileBehaviour {

    protected int number;

    public CheckPointBehaviour(
            Robot[] robotStates, Block[][] board, Position blockPos, int checkPointNumber) {
        super(robotStates, board, blockPos);
    }

    /**
     * when the old checkpoint is smaller than the new one, it changes new old one to the new
     * checkpoint. Changes the old CheckpointPosition to the new Position
     *
     * @author Tommy Dang and Finn Oldeboershuis
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 28.02.2023
     */
    @Override
    public List<MoveIntent> OnRobotEntered(int indexOfMovedRobot) {
        if (robotStates[indexOfMovedRobot].getLastCheckPoint() < number) {
            robotStates[indexOfMovedRobot].setLastCheckPoint(number);
            robotStates[indexOfMovedRobot].setLastCheckPointPosition(blockPos);
        } else {
            return null;
        }
        return null;
    }

    public int getCheckPointNumber() {
        // TODO
        return 0;
    }
}
