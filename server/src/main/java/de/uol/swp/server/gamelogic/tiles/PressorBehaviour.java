package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 24-02-2023
 */
public class PressorBehaviour extends AbstractTileBehaviour {

    private int[] activeInProgramSteps;
    private CardinalDirection direction;

    private boolean crossing;

    public PressorBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            int[] activeInProgramSteps,
            CardinalDirection direction,
            boolean crossing) {
        super(robotStates, board, blockPos);
        this.activeInProgramSteps = activeInProgramSteps;
        this.direction = direction;
        this.crossing = crossing;
    }

    /**
     * When the robot is under a pressor then it will die.
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 24-02-2023
     */
    @Override
    public List<MoveIntent> onPressorStage(int programStep) {
        for (int i : activeInProgramSteps) {
            if (i == programStep) {
                for (Robot robotState : robotStates) {
                    if (Objects.equals(robotState.getPosition(), blockPos)) {
                        robotState.setAlive(false);
                        break;
                    }
                }
                break;
            }
        }
        return null;
    }

    public boolean isCrossing() {
        return crossing;
    }

    /**
     * When the robot is under a pressor then it will die.
     *
     * @param activeInProgramSteps the current program step
     * @return null
     */

    @Override
    public List<int[]> getImage() {
        int type = 0;
        if (!this.crossing) {
            if (this.activeInProgramSteps[0] == 1) {
                type = 35;
            } else {
                type = 36;
            }
        } else {
            if (this.activeInProgramSteps[0] == 2) {
                type = 37;
            } else {
                type = 38;
            }
        }
        return new ArrayList<>(List.of(new int[] {type, direction.ordinal()}));
    }
}
