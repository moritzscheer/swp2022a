package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;
import de.uol.swp.common.game.enums.CardinalDirection;

import java.util.List;

public class ExpressConveyorBeltBehaviour extends ConveyorBeltBehaviour {

    public ExpressConveyorBeltBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            ArrowType arrowType,
            CardinalDirection direction) {
        super(robotStates, board, blockPos, arrowType, direction);
    }

    @Override
    public List<MoveIntent> onExpressConveyorStage(int programmStep) {
        return onConveyorStage(programmStep);
    }
}
