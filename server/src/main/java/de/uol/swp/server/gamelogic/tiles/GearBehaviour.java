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
 * Manages the gear block operation over the robots
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @see CardinalDirection
 * @since 06-02-2023
 */
public class GearBehaviour extends AbstractTileBehaviour {

    private final boolean turnClockwise;
    int type;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 06-02-2023
     */
    public GearBehaviour(
            List<Robot> robotStates, Block[][] board, Position blockPos, boolean turnC) {
        super(robotStates, board, blockPos);
        turnClockwise = turnC;
    }

    /**
     * Turn the robot given the robot original direction
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @since 06-02-2023
     */
    @Override
    public List<MoveIntent> onRotatorStage(int programStep) {
        for (Robot robotState : robotStates) {
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                if (this.turnClockwise) {
                    CardinalDirection dir = robotState.getDirection();
                    dir = CardinalDirection.values()[(dir.ordinal() + 1) % 4];
                    robotState.setDirection(dir);
                } else {
                    CardinalDirection dir = robotState.getDirection();
                    dir = CardinalDirection.values()[(dir.ordinal() + 3) % 4];
                    robotState.setDirection(dir);
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public List<int[]> getImage() {
        if (this.turnClockwise) {
            type = 34;
        } else {
            type = 33;
        }
        return new ArrayList<>(List.of(new int[] {type, 0}));
    }

    public boolean isTurnClockwise() {
        return turnClockwise;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
