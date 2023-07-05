package de.uol.swp.server.gamelogic.unitTest.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.ExpressConveyorBeltBehaviour;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ExpressConveyorBeltBehaviourTest {

    /**
     * Tests the onExpressConveyorStage method
     *
     * @author WKempel
     * @result The onExpressConveyorStage method should return true because the expectedMoves and
     *     actualMoves are the same
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void onExpressConveyorStageTest() {
        List<Robot> robots = new ArrayList<>();
        Block[][] board = new Block[12][12];
        Position blockPos = new Position(1, 1);
        ArrowType arrowType = ArrowType.Straight;
        CardinalDirection direction = CardinalDirection.East;

        ExpressConveyorBeltBehaviour expressConveyorBelt =
                new ExpressConveyorBeltBehaviour(robots, board, blockPos, arrowType, direction);

        List<MoveIntent> expectedMoves = new ArrayList<>();

        int programmStep = 0;
        List<MoveIntent> actualMoves = expressConveyorBelt.onExpressConveyorStage(programmStep);

        Assertions.assertEquals(expectedMoves, actualMoves);
    }
}
