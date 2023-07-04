package de.uol.swp.server.gamelogic.unitTest.tiles.enums;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckPointBehaviourTest {

    private CheckPointBehaviour checkPointBehaviour;
    private final List<Robot> robotStates = new ArrayList<>();
    private final Position blockPos = new Position(0, 0);

    private final CardinalDirection direction = CardinalDirection.North;
    private final int checkPointNumber = 1;

    private final Robot robot = new Robot(123, blockPos, direction);

    /**
     * Set up the test environment
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @BeforeEach
    public void setUp() {
        Block[][] board = new Block[12][12];
        checkPointBehaviour =
                new CheckPointBehaviour(robotStates, board, blockPos, checkPointNumber);
    }

    /**
     * Tests getCheckPointNumber method
     *
     * @author WKempel
     * @result The getCheckPointNumber method should return true if the checkPointNumber is correct
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testGetCheckPointNumber() {
        Assertions.assertEquals(checkPointNumber, checkPointBehaviour.getCheckPointNumber());
    }

    /**
     * Tests setCheckPoint method
     *
     * @author WKempel
     * @result The setCheckPoint method should return true if the checkPointNumber is correct
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testSetCheckPoint_NextCheckpoint() {
        robot.setLastCheckPoint(checkPointNumber - 1);
        robotStates.add(robot);
        int result = checkPointBehaviour.setCheckPoint(0);
        Assertions.assertEquals(checkPointNumber, result);
        Assertions.assertEquals(checkPointNumber, robot.getLastCheckPoint());
        Assertions.assertEquals(blockPos, robot.getLastBackupCopyPosition());
    }

    /**
     * Tests the getImage method
     *
     * @author WKempel
     * @result The getImage method should return true if the image is correct
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testGetImage() {
        List<int[]> image = checkPointBehaviour.getImage();
        Assertions.assertFalse(image.contains(new int[] {2 + checkPointNumber, 0}));
    }
}
