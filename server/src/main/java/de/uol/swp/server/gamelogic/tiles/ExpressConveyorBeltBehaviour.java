package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import java.util.List;

/**
 * @author Finn
 * @since 2023-03-05
 */
public class ExpressConveyorBeltBehaviour extends ConveyorBeltBehaviour {

    /**
     * @author Maria
     * @since 2023-03-05
     */
    public ExpressConveyorBeltBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            ArrowType arrowType,
            CardinalDirection direction) {
        super(robotStates, board, blockPos, arrowType, direction);
    }

    /**
     * @author WKempel
     * @since 2023-03-05
     */
    @Override
    public List<MoveIntent> onExpressConveyorStage(int programmStep) {
        return onConveyorStage(programmStep);
    }
}
