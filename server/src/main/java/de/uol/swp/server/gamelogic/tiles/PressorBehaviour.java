package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.List;
import java.util.Objects;

/**
 * @author WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 24-02-2023
 */
public class PressorBehaviour extends AbstractTileBehaviour {

    private int[] activeInProgramSteps;

    public PressorBehaviour(
            Robot[] robotStates, Block[][] board, int[] activeInProgramSteps, Position blockPos) {
        super(robotStates, board, blockPos);
        this.activeInProgramSteps = activeInProgramSteps;
    }

    /**
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 24-02-2023
     */
    @Override
    public List<MoveIntent> OnPresserStage(int programStep) {
        for (int i : activeInProgramSteps) {
            if (i == programStep) {
                for (Robot robotState : robotStates) {
                    if (Objects.equals(robotState.getPosition(), blockPos)) {
                        robotState.setAlive(false);
                        return null;
                    }
                }
                break;
            }
        }
        return null;
    }
}
