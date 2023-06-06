package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.ArrayList;
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
            List<Robot> robotStates, Block[][] board, Position blockPos, int checkPointNumber) {
        super(robotStates, board, blockPos);
        this.number = checkPointNumber;
    }

    public int getCheckPointNumber() {
        return number;
    }

    /**
     * when the old checkpoint is smaller than the new one, it changes new old one to the new
     * checkpoint. Changes the old CheckpointPosition to the new Position
     *
     * @author Tommy Dang and Finn Oldeboershuis
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 28.02.2023
     */
    public int setCheckPoint(int indexOfMovedRobot) {
        if (robotStates.get(indexOfMovedRobot).getLastCheckPoint() < this.number) {
            robotStates.get(indexOfMovedRobot).setLastCheckPoint(this.number);
            robotStates.get(indexOfMovedRobot).setLastCheckPointPosition(blockPos);
            robotStates.get(indexOfMovedRobot).setBackupCopy(true);
            return this.number;
        }
        return 0;
    }

    @Override
    public List<int[]> getImage(){
        return new ArrayList<>(List.of(new int[]{2 + number, 0}));
    }
}
