package de.uol.swp.server.gamelogic.tiles.unitTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.PressorBehaviour;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setUp() throws Exception {
        robotStates = new Robot[1];
        robotStates[0] = new Robot("Pressor", new Position(0, 0), true, CardinalDirection.East);
        board = new Block[1][1];
        blockPos = new Position(0, 0);
        pressorBehaviour = new PressorBehaviour(robotStates, board, activeInProgramSteps, blockPos);
    }

    /**
     * Test is the robot still alive despite he is standing on the block
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 25-02-2023
     */
    @Test
    public void testKillRobotWhenRobotIsOnBlock() {
        robotStates[0].setCurrentPosition(blockPos);
        assertTrue(pressorBehaviour.killRobot(1));
        assertFalse(robotStates[0].isAlive());
    }

    /**
     * Test is the robot still alive when he is not standing on the block
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 25-02-2023
     */
    @Test
    public void testKillRobotWhenRobotIsNotOnBlock() {
        robotStates[0].setCurrentPosition(new Position(1, 1));
        assertFalse(pressorBehaviour.killRobot(1));
        assertTrue(robotStates[0].isAlive());
    }

    /**
     * Test is the robot still alive when he is standing on the block and the program step is not
     * activated
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.PressorBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 26-02-2023
     */
    @Test
    public void testKillRobotWhenRobotIsNotInProgramStep() {
        robotStates[0].setCurrentPosition(new Position(1, 1));
        assertFalse(pressorBehaviour.killRobot(3));
        assertTrue(robotStates[0].isAlive());
    }
}
