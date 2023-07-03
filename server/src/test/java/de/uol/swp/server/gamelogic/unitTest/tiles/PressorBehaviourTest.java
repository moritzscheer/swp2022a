package de.uol.swp.server.gamelogic.unitTest.tiles;

import static junit.framework.TestCase.assertNull;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.PressorBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test PressorBehaviour
 *
 * @author WKempel
 * @since 25-02-2023
 */
public class PressorBehaviourTest {

    private Robot[] robotStates;
    private Block[][] board;
    private Position blockPos;

    private int[] activeInProgramSteps = new int[] {1, 2};
    private PressorBehaviour pressorBehaviour;
    private PressorBehaviour pressorBehaviour2;

    /**
     * Set up the test
     *
     * @author WKempel
     * @throws Exception
     * @since 2023-02-25
     */
    @Before
    public void setUp() throws Exception {
        robotStates = new Robot[1];
        robotStates[0] = new Robot(1, new Position(0, 0), CardinalDirection.East);
        board = new Block[1][1];
        blockPos = new Position(0, 0);
        pressorBehaviour =
                new PressorBehaviour(
                        List.of(robotStates),
                        board,
                        blockPos,
                        activeInProgramSteps,
                        CardinalDirection.West,
                        true);
        pressorBehaviour2 =
                new PressorBehaviour(
                        List.of(robotStates),
                        board,
                        blockPos,
                        activeInProgramSteps,
                        CardinalDirection.West,
                        false);
    }

    /**
     * Test is the robot still alive despite he is standing on the block
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-02-25
     */
    @Test
    public void testKillRobotWhenRobotIsOnBlock() {
        robotStates[0].setCurrentPosition(blockPos);
        assertNull(pressorBehaviour.onPressorStage(1));
        assertFalse(robotStates[0].isAlive());
    }

    /**
     * Test is the robot still alive when he is not standing on the block
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-25
     */
    @Test
    public void testKillRobotWhenRobotIsNotOnBlock() {
        robotStates[0].setCurrentPosition(new Position(1, 1));
        assertNull(pressorBehaviour.onPressorStage(1));
        assertTrue(robotStates[0].isAlive());
    }

    /**
     * Test is the robot still alive when he is standing on the block and the program step is not
     * activated
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-26
     */
    @Test
    public void testKillRobotWhenRobotIsNotInProgramStep() {
        robotStates[0].setCurrentPosition(new Position(1, 1));
        assertNull(pressorBehaviour.onPressorStage(3));
        assertTrue(robotStates[0].isAlive());
    }

    /**
     * Tests the getImage method
     *
     * @author WKempel
     * @result The correct image is returned
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @since 2023-06-21
     */
    @Test
    public void testGetImage() {
        List<int[]> image = new ArrayList<>();
        if (!pressorBehaviour2.isCrossing()) {
            image = pressorBehaviour2.getImage();
            if (activeInProgramSteps[0] == 1) {
                assertEquals(35, image.get(0)[0]);
            } else {
                assertEquals(36, image.get(0)[0]);
            }
        } else {
            image = pressorBehaviour.getImage();
            if (activeInProgramSteps[0] == 2) {
                assertEquals(37, image.get(0)[0]);
            } else {
                assertEquals(38, image.get(0)[0]);
            }
        }
    }
}
