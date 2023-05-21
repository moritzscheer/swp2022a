package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;
import de.uol.swp.common.game.enums.CardinalDirection;

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
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                moves.add(new MoveIntent(robotState.getID(), direction));

                // rotate robot if moved on other Conv. Belt
                try {
                    Position targetPos = Position.translate(blockPos, direction);
                    if (targetPos != null) {
                        Block nextBlock = board[targetPos.x][targetPos.y];
                        ConveyorBeltBehaviour conBehaviourOnNextBlock;
                        try {
                            conBehaviourOnNextBlock =
                                    nextBlock.GetBehaviour(
                                            (Class<ConveyorBeltBehaviour>) this.getClass());
                        } catch (NullPointerException exp) {
                            // next block does not exist
                            conBehaviourOnNextBlock = null;
                        }

                        if (conBehaviourOnNextBlock != null) {
                            int rotation =
                                    (conBehaviourOnNextBlock.direction.ordinal()
                                            - direction.ordinal() + 4) % 4 ;
                            robotState.setDirection(
                                    CardinalDirection.values()[
                                            robotState.getDirection().ordinal() + rotation]);
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

    @Override
    public List<int[]> getImage() {
        int rotation = direction.ordinal() ;
        int arrowType;
        int secondArrowType = 0;
        boolean hasSecondArrow = false;
        int type = 9;
        if(this instanceof ExpressConveyorBeltBehaviour){
            type = 26;
        }
        switch (this.arrowType){

            case Straight:
                if(this instanceof ExpressConveyorBeltBehaviour){
                    arrowType = 27;
                }
                else {
                    arrowType = 10;
                }
                break;
            case TurnRight:
                if(this instanceof ExpressConveyorBeltBehaviour){
                    arrowType = 28;
                }
                else {
                    arrowType = 11;
                }
                break;
            case TurnLeft:
                if(this instanceof ExpressConveyorBeltBehaviour){
                    arrowType = 29;
                }
                else {
                    arrowType = 12;
                }
                break;
            case StraightTurnRight:
                if(this instanceof ExpressConveyorBeltBehaviour){
                    arrowType = 27;
                    hasSecondArrow = true;
                    secondArrowType = 28;
                }
                else {
                    arrowType = 10;
                    hasSecondArrow = true;
                    secondArrowType = 11;
                }
                break;
            case StraightTurnLeft:
                if(this instanceof ExpressConveyorBeltBehaviour){
                    arrowType = 27;
                    hasSecondArrow = true;
                    secondArrowType = 29;
                }
                else {
                    arrowType = 10;
                    hasSecondArrow = true;
                    secondArrowType = 12;
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + this.arrowType + " or ");
        }
        if(hasSecondArrow){
            return new ArrayList<>(Arrays.asList( new int[] {type, rotation}, new int[] {arrowType, rotation}, new int[] {secondArrowType, rotation}));
        }
        return new ArrayList<>(Arrays.asList( new int[] {type, rotation}, new int[] {arrowType, rotation}));
    }
}
