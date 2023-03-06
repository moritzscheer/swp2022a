package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.List;

public class ExpressConveyorBeltBehaviour extends  ConveyorBeltBehaviour{

    public ExpressConveyorBeltBehaviour(Robot[] robotStates, Block[][] board, Position blockPos) {
        super(robotStates, board, blockPos);
    }

    @Override
    public List<MoveIntent> OnExpressConveyorStage(int programmStep) {
        return OnConveyorStage();
    }
}
