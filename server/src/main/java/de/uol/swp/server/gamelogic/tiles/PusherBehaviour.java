package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * If a robot steps on the pusher tile, then the robot gets pushed in the specific direction
 *
 * @author Tommy Dang
 * @since 28.02.2023
 */
public class PusherBehaviour extends AbstractTileBehaviour {

    private final List<Integer> activeInProgramSteps;
    private final CardinalDirection direction;
    private final CardinalDirection pushingDirection;

    /**
     * @author Maria
     * @since 2023-03-05
     */
    public PusherBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            int[] activeInProgramSteps,
            CardinalDirection direction) {
        super(robotStates, board, blockPos);
        this.activeInProgramSteps =
                Arrays.stream(activeInProgramSteps).boxed().collect(Collectors.toList());
        this.direction = direction;
        this.pushingDirection = CardinalDirection.values()[(direction.ordinal() + 2) % 4];
    }

    /**
     * When a robot is on the pusher tile detected, then it pushes the robot in the direction of the
     * pusher.
     *
     * @author Tommy Dang and Finn Oldeboershuis
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @see MoveIntent
     * @since 28.02.2023
     */
    @Override
    public List<MoveIntent> onPusherStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        if (this.activeInProgramSteps.contains(programStep)) {
            for (Robot robotState : this.robotStates) {
                if (!robotState.isAlive()) continue;
                if (Objects.equals(robotState.getPosition(), this.blockPos)) {
                    moves.add(new MoveIntent(robotState.getID(), this.pushingDirection));
                    break;
                }
            }
            return moves;
        }
        return null;
    }

    /**
     * @author Maria
     * @since 2023-03-05
     */
    public CardinalDirection getDirection() {
        return this.direction;
    }

    /**
     * @author Maria
     * @since 2023-06-18
     */
    public CardinalDirection getPushingDirection() {
        return this.pushingDirection;
    }

    /**
     * @author Maria
     * @since 2023-06-12
     */
    public List<Integer> getActiveInProgramSteps() {
        return this.activeInProgramSteps;
    }

    /**
     * @author Merden
     * @since 2023-05-21
     */
    @Override
    public List<int[]> getImage() {
        int type;
        if (this.activeInProgramSteps.size() == 3) {
            type = 42;
        } else if (this.activeInProgramSteps.size() == 2) {
            type = 43;
        } else {
            type = this.activeInProgramSteps.get(0) + 38;
        }
        return new ArrayList<>(List.of(new int[] {type, direction.ordinal()}));
    }
}
