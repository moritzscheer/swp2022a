package de.uol.swp.server.gamelogic.unitTest.tiles;

import static junit.framework.TestCase.*;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.PitBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PitBehaviourTest {

    private Robot[] robotStates;

    private Block[][] board;

    private Position blockPos;

    private PitBehaviour pitBehaviour;
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];

    /**
     * Set up the test
     *
     * @author WKempel
     * @throws Exception
     * @since 2023-02-26
     */
    @Before
    public void SetUp() throws Exception {
        robotStates = new Robot[1];
        robotStates[0] = new Robot(1, new Position(0, 0), CardinalDirection.East);
        board = new Block[1][1];
        blockPos = new Position(0, 0);
        pitBehaviour = new PitBehaviour(List.of(robotStates), board, blockPos);
        behaviours1[0] = pitBehaviour;
    }

    /**
     * Test is the robot still alive despite he is standing on the block
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PitBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-26
     */
    @Test
    public void testKillRobotWhenRobotIsOnBlock() {
        robotStates[0].setCurrentPosition(blockPos);
        assertNull(pitBehaviour.onRobotEntered(1));
        assertFalse(robotStates[0].isAlive());
    }
}
