package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.Objects;

/**
 * Manages the gear block operation over the robots
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @see de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection
 * @since 06-02-2023
 */
public class GearBehaviour extends AbstractTileBehaviour {

    private final boolean turnClockwise;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 06-02-2023
     */
    public GearBehaviour(Robot[] robotStates, Block[][] board, Position blockPos, boolean turnC) {
        super(robotStates, board, blockPos);
        this.turnClockwise = turnC;
    }

    /**
     * Turn the robot given the robot original direction
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection
     * @since 06-02-2023
     */
    public void turnRobot() {
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
                return;
            }
        }
    }
}
