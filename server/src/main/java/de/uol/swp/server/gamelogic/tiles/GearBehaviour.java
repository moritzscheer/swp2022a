package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public GearBehaviour(List<Robot> robotStates, Block[][] board, Position blockPos, boolean turnC) {
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

    //TODO: Gea bilder fehlen in Tile.json

//    @Override
//    public List<int[]> getImage() {
//        return new ArrayList<>(List.of(new int[] {turnClockwise ? , 0}));
//    }
}
