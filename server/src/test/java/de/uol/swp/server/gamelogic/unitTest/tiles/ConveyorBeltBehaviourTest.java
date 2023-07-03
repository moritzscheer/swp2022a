package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test ConveyorBeltBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-03-13
 */
public class ConveyorBeltBehaviourTest {
    private static final Robot[] robots = new Robot[3];
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours2 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours3 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours4 = new AbstractTileBehaviour[1];

    private static final Block[][] board = new Block[2][5];

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot(1, new Position(1, 1), CardinalDirection.East);
        robots[1] = new Robot(2, new Position(1, 4), CardinalDirection.West);
        robots[2] = new Robot(3, new Position(2, 1), CardinalDirection.North);

        behaviours1[0] =
                new ConveyorBeltBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 1),
                        ArrowType.Straight,
                        CardinalDirection.East);
        behaviours2[0] =
                new ConveyorBeltBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 2),
                        ArrowType.Straight,
                        CardinalDirection.East);
        behaviours3[0] =
                new ConveyorBeltBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 4),
                        ArrowType.Straight,
                        CardinalDirection.West);
        behaviours4[0] =
                new ConveyorBeltBehaviour(
                        List.of(robots),
                        board,
                        new Position(2, 1),
                        ArrowType.TurnRight,
                        CardinalDirection.North);

        board[0][0] = new Block(behaviours1, "", new Position(1, 1));
        board[0][1] = new Block(behaviours2, "", new Position(1, 2));
        board[0][3] = new Block(behaviours3, "", new Position(1, 4));
        board[1][0] = new Block(behaviours4, "", new Position(2, 1));
    }

    /**
     * Test move robot in conveyor belt
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.enums.ArrowType
     * @see de.uol.swp.server.gamelogic.tiles.ConveyorBeltBehaviour
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-13
     */
    @Test
    public void moveRobot() {
        // move robot in conveyorBelt
        assertEquals(robots[0].getPosition(), behaviours1[0].getBlockPos());
        List<MoveIntent> moveIntents = ((ConveyorBeltBehaviour) behaviours1[0]).onConveyorStage(1);
        assertEquals(1, moveIntents.size());
        MoveIntent moveIntent = moveIntents.get(0);
        assertEquals(robots[0].getID(), moveIntent.robotID);
        assertEquals(CardinalDirection.East, moveIntent.getDirection());
        // TODO: Must be tested with move intent
        // assertEquals(robots[0].getPosition(), behaviours2[0].getBlockPos());
    }

    /**
     * Test try to move two robots in conveyor belts against each other
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.enums.ArrowType
     * @see de.uol.swp.server.gamelogic.tiles.ConveyorBeltBehaviour
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-13
     */
    @Test
    public void moveTwoRobotAgainstEachOther() {
        // this must be implemented calling move intents, beacuse the move happens
        // only later
    }

    /**
     * Test move robot in conveyor belt
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.enums.ArrowType
     * @see de.uol.swp.server.gamelogic.tiles.ConveyorBeltBehaviour
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-13
     */
    @Test
    public void moveTurnRobot() {
        // move robot in conveyorBelt
        assertEquals(robots[2].getPosition(), behaviours4[0].getBlockPos());
        assertEquals(robots[2].getDirection(), CardinalDirection.North);
        List<MoveIntent> moveIntents = behaviours4[0].onConveyorStage(1);
        assertEquals(1, moveIntents.size());
        MoveIntent moveIntent = moveIntents.get(0);
        assertEquals(robots[2].getID(), moveIntent.robotID);
        assertEquals(CardinalDirection.North, moveIntent.getDirection());
        // TODO: must be tested with move intent
        //        assertEquals(robots[2].getPosition(), new Position(2, 2));
        //        assertEquals(robots[2].getDirection(), CardinalDirection.East);
    }

    @Test
    public void getImageTest() {
        List<Robot> robots = new ArrayList<>();
        Block[][] board = new Block[12][12];

        ConveyorBeltBehaviour conveyorBelt =
                new ConveyorBeltBehaviour(
                        robots,
                        board,
                        new Position(1, 1),
                        ArrowType.Straight,
                        CardinalDirection.East);

        List<int[]> expectedImage = new ArrayList<>();

        if (conveyorBelt.getArrowType() == ArrowType.Straight) {
            expectedImage = conveyorBelt.getImage();
            assertEquals(9, expectedImage.get(0)[0]);
        } else if (conveyorBelt.getArrowType() == ArrowType.TurnRight) {
            expectedImage = conveyorBelt.getImage();
            assertEquals(11, expectedImage.get(0)[0]);
        } else if (conveyorBelt.getArrowType() == ArrowType.TurnLeft) {
            expectedImage = conveyorBelt.getImage();
            assertEquals(12, expectedImage.get(0)[0]);
        } else if (conveyorBelt.getArrowType() == ArrowType.StraightTurnRight) {
            expectedImage = conveyorBelt.getImage();
            assertEquals(15, expectedImage.get(0)[0]);
        } else if (conveyorBelt.getArrowType() == ArrowType.StraightTurnLeft) {
            expectedImage = conveyorBelt.getImage();
            assertEquals(14, expectedImage.get(0)[0]);
        } else if (conveyorBelt.getArrowType() == ArrowType.TTurn) {
            expectedImage = conveyorBelt.getImage();
            assertEquals(52, expectedImage.get(0)[0]);
        }
    }
}
