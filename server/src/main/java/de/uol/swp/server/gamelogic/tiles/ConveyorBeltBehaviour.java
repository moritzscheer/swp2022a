package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Finn
 * @since 2023/02/03
 */
public class ConveyorBeltBehaviour extends AbstractTileBehaviour {

    private ArrowType arrowType;
    private CardinalDirection direction;
    private boolean express;

    public ConveyorBeltBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
    }

    /**
     * @author Finn
     * @since 2023/02/03
     */
    @Override
    public List<MoveIntent> OnConveyorStage() {
        List<MoveIntent> moves = new ArrayList<>();
        for (Robot robotState : robotStates) {
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                moves.add(new MoveIntent(robotState.getID(), direction));
                break;
            }
        }
        return moves;
    }

    @Override
    public List<MoveIntent> OnExpressConveyorStage() {
        return OnConveyorStage();
    }
}
