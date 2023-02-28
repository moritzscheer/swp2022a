package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * If a robot steps on the pusher tile, then the robot gets pushed in the specific direction
 *
 * @author Tommy Dang
 * @since 28.02.2023
 */
public class PusherBehaviour extends AbstractTileBehaviour {

    private int[] activeInProgramSteps;
    private CardinalDirection direction;

    public PusherBehaviour(
            Robot[] robotStates, Block[][] board, de.uol.swp.server.gamelogic.Position blockPos) {
        super(robotStates, board, blockPos);
    }

    /**
     * When a robot is on the pusher tile detected, then it pushes the robot in the direction of the pusher.
     *
     * @author Tommy Dang and Finn Oldeboershuis
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @see de.uol.swp.server.gamelogic.MoveIntent
     * @since 28.02.2023
     */
    @Override
    public List<MoveIntent> OnPusherStage(){
        List<MoveIntent> moves = new ArrayList<>();
        for (Robot robotState : robotStates) {
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                moves.add(new MoveIntent(robotState.getID(), direction));
                break;
            }
        }
        return moves;
    }
}
