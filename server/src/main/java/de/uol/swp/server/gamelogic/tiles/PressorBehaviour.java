package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.Objects;

/**
 * @author WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 24-02-2023
 */
public class PressorBehaviour extends AbstractTileBehaviour {

    private int[] activeInProgramSteps;
    private boolean isActiveStep = false;

    public PressorBehaviour(Robot[] robotStates, Block[][] board, Position blockPos, boolean isActiveStep) {
        super(robotStates, board, blockPos);
        this.isActiveStep = isActiveStep;
    }

    /**
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 24-02-2023
     */
    private boolean killRobot(int programStep) {
        for (int i : activeInProgramSteps) {
            if (i == programStep) {
                isActiveStep = true;
                break;
            }
            for (Robot robotState : robotStates) {
                if (Objects.equals(robotState.getPosition(), blockPos)) {
                    robotState.setAlive(false);
                }
            }
        }
        return true;
    }
}
