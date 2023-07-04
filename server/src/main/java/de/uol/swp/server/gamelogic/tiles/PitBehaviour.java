package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 25-02-2023
 */
public class PitBehaviour extends AbstractTileBehaviour {

    public PitBehaviour(List<Robot> robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
    }

    /**
     * When a robot fall in the pit then it will die.
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 25-02-2023
     */
    @Override
    public List<MoveIntent> onRobotEntered(int indexMoveRobot) {
        for (Robot robotState : robotStates) {
            if (!robotState.isAlive()) continue;
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                robotState.setAlive(false);
                break;
            }
        }
        return null;
    }

    /**
     * @author Maria
     * @since 2023-03-05
     */
    @Override
    public List<int[]> getImage() {
        return new ArrayList<>(List.of(new int[] {2, 0}));
    }
}
