package de.uol.swp.server.gamelogic.tiles;

import com.mysql.cj.log.Log;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Finn
 * @since 2023/02/03
 */
public class ConveyorBeltBehaviour extends AbstractTileBehaviour {

    private ArrowType arrowType;
    private CardinalDirection direction;

    /**
     * Constructor
     *
     * @author Finn
     * @since 2023/02/03
     */
    public ConveyorBeltBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            ArrowType arrowType,
            CardinalDirection direction) {
        super(robotStates, board, blockPos);
        this.arrowType = arrowType;
        this.direction = direction;
    }

    /**
     * Create a move intention of the robot to the next block
     *
     * @author Finn
     * @since 2023/02/03
     */
    @Override
    public List<MoveIntent> onConveyorStage(int programmStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (Robot robotState : robotStates) {
            if (!robotState.isAlive())
                continue;
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                moves.add(new MoveIntent(robotState.getID(), direction));

                // rotate robot if moved on other Conv. Belt
                Position targetPos = Position.translate(blockPos, direction);
                if (targetPos != null && targetPos.x < board.length && targetPos.x >= 0 && targetPos.y < board[targetPos.x].length && targetPos.y >= 0) {
                    Block nextBlock = board[targetPos.x][targetPos.y];
                    ConveyorBeltBehaviour conBehaviourOnNextBlock;

                    try {
                        conBehaviourOnNextBlock = nextBlock.GetBehaviour((Class<ConveyorBeltBehaviour>) this.getClass());
                    } catch (NullPointerException exp) {
                        // next block does not have a conveyor
                        conBehaviourOnNextBlock = null;
                    }

                    if (conBehaviourOnNextBlock != null) {
                        int rotation = (conBehaviourOnNextBlock.direction.ordinal() - direction.ordinal() + 4) % 4;
                        System.out.println("Rotation could happen now");
                        robotState.setDirection(CardinalDirection.values()[(robotState.getDirection().ordinal() + rotation) % 4]);
                    }
                }

                break;
            }
        }
        return moves;
    }

    /**
     * @author
     * @since 2023-03-05
     */
    @Override
    public List<int[]> getImage() {
        int rotation = direction.ordinal();
        int arrowType;
        int secondArrowType = 0;
        boolean hasSecondArrow = false;
        int type = 9;
        if (this instanceof ExpressConveyorBeltBehaviour) {
            type = 26;
        }
        switch (this.arrowType) {
            case Straight:
                if (this instanceof ExpressConveyorBeltBehaviour) {
                    arrowType = 27;
                } else {
                    arrowType = 10;
                }
                break;
            case TurnRight:
                if (this instanceof ExpressConveyorBeltBehaviour) {
                    arrowType = 28;
                } else {
                    arrowType = 11;
                }
                break;
            case TurnLeft:
                if (this instanceof ExpressConveyorBeltBehaviour) {
                    arrowType = 29;
                } else {
                    arrowType = 12;
                }
                break;
            case StraightTurnRight:
                if (this instanceof ExpressConveyorBeltBehaviour) {
                    arrowType = 31;
                } else {
                    arrowType = 15;
                }
                break;
            case StraightTurnLeft:
                if (this instanceof ExpressConveyorBeltBehaviour) {
                    arrowType = 30;
                } else {
                    arrowType = 14;
                }
                break;

            case TTurn:
                if (this instanceof ExpressConveyorBeltBehaviour) {
                    arrowType = 53;
                } else {
                    arrowType = 52;
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + this.arrowType + " or ");
        }

        return new ArrayList<>(
                Arrays.asList(new int[]{type, rotation}, new int[]{arrowType, rotation}));
    }

    public ArrowType getArrowType() {
        return arrowType;
    }

    public void setArrowType(ArrowType arrowType) {
        this.arrowType = arrowType;
    }
}
