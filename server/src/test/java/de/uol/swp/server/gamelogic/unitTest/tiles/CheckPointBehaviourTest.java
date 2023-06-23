package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test CheckPointBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-02-26
 */
public class CheckPointBehaviourTest {
    private static final Position pos1 = new Position(1, 1);
    private static final Robot[] robots = new Robot[1];
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];
    private static final Block[][] board = new Block[1][1];
    private static final int checkPointNumber = 1;
    private static final int checkPointCount = 4;

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot(1, pos1, CardinalDirection.East);
        behaviours1[0] = new CheckPointBehaviour(List.of(robots), board, pos1, checkPointNumber);
        board[0][0] = new Block(behaviours1, "", pos1);
    }

    /**
     * Test when robot arrives in check point
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-26
     */
    @Test
    public void getRobotLastCheckPointTest() {
        // robot is in checkpoint position
        ((CheckPointBehaviour) behaviours1[0]).setCheckPoint(0);
        assertEquals(checkPointNumber, robots[0].getLastCheckPoint());
    }

    /**
     * Test after robot passed check point and dies
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-26
     */
    @Test
    public void robotDiesTest() {
        // robot is in checkpoint position
        ((CheckPointBehaviour) behaviours1[0]).setCheckPoint(0);
        assertEquals(
                ((CheckPointBehaviour) behaviours1[0]).getCheckPointNumber(),
                robots[0].getLastCheckPoint());

        // robot dies
        robots[0].setAlive(false);
        assertEquals(behaviours1[0].getBlockPos(), robots[0].getLastBackupCopyPosition());
    }

    /**
     * Test after robot passed check point and dies and comes back for next round
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-26
     */
    @Test
    public void robotDiesAndComeBackTest() {
        // robot is in checkpoint position
        ((CheckPointBehaviour) behaviours1[0]).setCheckPoint(0);
        assertEquals(
                ((CheckPointBehaviour) behaviours1[0]).getCheckPointNumber(),
                robots[0].getLastCheckPoint());

        // robot dies
        robots[0].setAlive(false);
        assertEquals(behaviours1[0].getBlockPos(), robots[0].getLastBackupCopyPosition());

        // new round, robot comes back in last check point
        robots[0].setAlive(true);
        assertEquals(behaviours1[0].getBlockPos(), robots[0].getPosition());
    }
}
