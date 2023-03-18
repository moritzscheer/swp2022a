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

    public ConveyorBeltBehaviour(
            Robot[] robotStates,
            Block[][] board,
            Position blockPos,
            ArrowType arrowType,
            CardinalDirection direction) {
        super(robotStates, board, blockPos);
    }

    /**
     * @author Finn
     * @since 2023/02/03
     */
    @Override
    public List<MoveIntent> onConveyorStage(int programmStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (Robot robotState : robotStates) {
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                moves.add(new MoveIntent(robotState.getID(), direction));

                //rotate robot if moved on other Conv. Belt
                try {
                    Position targetPos = Position.translate(blockPos, direction);
                    if (targetPos != null) {
                        Block nextBlock = board[targetPos.x][targetPos.y];
                        ConveyorBeltBehaviour conBehaviourOnNextBlock = nextBlock.GetBehaviour((Class<ConveyorBeltBehaviour>) this.getClass());
                        if (conBehaviourOnNextBlock != null) {
                            int rotation = conBehaviourOnNextBlock.direction.ordinal() - direction.ordinal();
                            robotState.setDirection(CardinalDirection.values()[robotState.getDirection().ordinal() + rotation]);
                        }
                    }
                } catch (IndexOutOfBoundsException exp) {
                    //
                }

                break;
            }
        }
        return moves;
    }
}
