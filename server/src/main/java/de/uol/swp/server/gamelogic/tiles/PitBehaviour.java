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
 * @since 25-02-2023
 */
public class PitBehaviour extends AbstractTileBehaviour {

    public PitBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
    }

    /**
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 25-02-2023
     */
    @Override
    public List<MoveIntent> OnRobotEntered(int indexMoveRobot) {
        for (Robot robotState : robotStates) {
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                robotState.setAlive(false);
                break;
            }
        }
        return null;
    }
}
