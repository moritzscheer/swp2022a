package de.uol.swp.server.gamelogic.unitTest.tiles;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.LaserBehaviour;
import de.uol.swp.server.gamelogic.tiles.WallBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test LaserBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-03-13
 */
public class LaserBehaviourTest {

    private static final Position pos1 = new Position(0, 0);
    private static final Position pos2 = new Position(0, 1);
    private static final Position pos3 = new Position(2, 3);
    private static final Robot[] robots = new Robot[3];
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours2 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours3 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours4 = new AbstractTileBehaviour[1];
    private static final Block[][] board = new Block[2][5];

    private final int[] activeInProgramSteps = new int[] {1, 2};
    private static final CardinalDirection laserDir = CardinalDirection.East;
    private static final int laserBeam = 1;
    private static final CardinalDirection wallDirection = CardinalDirection.West;

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot(1, pos1, CardinalDirection.East);
        robots[1] = new Robot(2, pos2, CardinalDirection.East);
        robots[2] = new Robot(3, pos3, CardinalDirection.East);
        behaviours1[0] =
                new LaserBehaviour(
                        List.of(robots),
                        board,
                        activeInProgramSteps,
                        pos1,
                        laserDir,
                        laserBeam,
                        true,
                        true);
        behaviours3[0] = new WallBehaviour(List.of(robots), board, pos3, wallDirection);
        behaviours4[0] =
                new LaserBehaviour(
                        List.of(robots),
                        board,
                        activeInProgramSteps,
                        pos1,
                        laserDir,
                        laserBeam,
                        true,
                        true);
        board[0][0] = new Block(behaviours1, "", pos1);
        board[0][1] = new Block(behaviours2, "", pos2);
        board[1][2] = new Block(behaviours3, "", pos3);
        board[1][0] = new Block(behaviours4, "", new Position(1, 0));
    }

    /**
     * Test damage robot direct in laser block
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see LaserBehaviour
     * @see Robot
     * @since 2023-03-13
     */
    @Test
    public void testLaserDamageRobot() {
        // robot 1 in same block as laser
        assertEquals(robots[0].getPosition(), behaviours1[0].getBlockPos());
        int beforeDamage = robots[0].getDamageToken();
        behaviours1[0].onLaserStage(1);
        int afterDamage = robots[0].getDamageToken();
        assertEquals(((LaserBehaviour) behaviours1[0]).getLaserBeam(), afterDamage - beforeDamage);
    }

    /**
     * Test not damage second robot, because first was damaged
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.LaserBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-03-13
     */
    @Test
    public void testNotDamageSecondRobot() {
        assertEquals(robots[0].getPosition(), behaviours1[0].getBlockPos());
        assertEquals(robots[1].getPosition(), pos2);
        int beforeDamage = robots[1].getDamageToken();
        behaviours1[0].onLaserStage(0);
        int afterDamage = robots[1].getDamageToken();
        assertEquals(0, beforeDamage - afterDamage);
    }

    /**
     * Test not damage second robot, because there is a wall
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.LaserBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-03-13
     */
    @Test
    public void testNotDamageSecondRobotWall() {
        // robot in same block as wall
        assertEquals(robots[2].getPosition(), behaviours3[0].getBlockPos());
        int beforeDamage = robots[2].getDamageToken();
        behaviours4[0].onLaserStage(0);
        int afterDamage = robots[2].getDamageToken();
        assertEquals(0, beforeDamage - afterDamage);
    }

    /**
     * Tests the getLaserDirection method.
     *
     * @author WKempel
     * @result The laser direction is returned correctly when the cardinal direction and laser
     *     behaviour has the same direction.
     * @since 2023-06-21
     */
    @Test
    public void testGetLaserDirection() {
        LaserBehaviour laserBehaviour =
                new LaserBehaviour(
                        new ArrayList<>(),
                        board,
                        activeInProgramSteps,
                        pos1,
                        CardinalDirection.North,
                        1,
                        true);
        assertEquals(CardinalDirection.North, laserBehaviour.getLaserDirection());

        laserBehaviour =
                new LaserBehaviour(
                        new ArrayList<>(),
                        board,
                        activeInProgramSteps,
                        pos2,
                        CardinalDirection.West,
                        2,
                        false);
        assertEquals(CardinalDirection.West, laserBehaviour.getLaserDirection());
    }

    /**
     * Tests the getLaserBeam method.
     *
     * @author WKempel
     * @result The laser beam is returned correctly because the expected value is the same as in the
     *     constructor.
     * @since 2023-06-21
     */
    @Test
    public void testGetLaserBeam() {
        LaserBehaviour laserBehaviour =
                new LaserBehaviour(
                        new ArrayList<>(),
                        board,
                        activeInProgramSteps,
                        pos1,
                        CardinalDirection.North,
                        1,
                        true);
        assertEquals(1, laserBehaviour.getLaserBeam());

        laserBehaviour =
                new LaserBehaviour(
                        new ArrayList<>(),
                        board,
                        activeInProgramSteps,
                        pos2,
                        CardinalDirection.West,
                        2,
                        false);
        assertEquals(2, laserBehaviour.getLaserBeam());
    }

    /**
     * Tests the getSetStart method.
     *
     * @author WKempel
     * @result The getStart method returns true or false after setting it.
     * @since 2023-06-21
     */
    @Test
    public void testGetSetStart() {
        LaserBehaviour laserBehaviour = (LaserBehaviour) behaviours1[0];
        assertTrue(laserBehaviour.getStart());

        laserBehaviour.setStart(false);
        assertFalse(laserBehaviour.getStart());

        laserBehaviour.setStart(true);
        assertTrue(laserBehaviour.getStart());
    }

    /**
     * Tests the setLaserBeam method.
     *
     * @author WKempel
     * @result The laser beam is set correctly.
     * @since 2023-06-21
     */
    @Test
    public void testSetLaserBeam() {
        LaserBehaviour laserBehaviour =
                new LaserBehaviour(
                        new ArrayList<>(),
                        board,
                        activeInProgramSteps,
                        pos1,
                        CardinalDirection.North,
                        1,
                        true);
        assertEquals(1, laserBehaviour.getLaserBeam());

        laserBehaviour.setLaserBeam(3);
        assertEquals(3, laserBehaviour.getLaserBeam());

        laserBehaviour.setLaserBeam(0);
        assertEquals(0, laserBehaviour.getLaserBeam());
    }

    /**
     * Tests the getImage method.
     *
     * @author WKempel
     * @result The image is returned correctly.
     * @since 2023-06-21
     */
    @Test
    public void testGetImage() {
        LaserBehaviour laserBehaviour =
                new LaserBehaviour(
                        new ArrayList<>(),
                        board,
                        activeInProgramSteps,
                        pos1,
                        CardinalDirection.North,
                        1,
                        true);
        List<int[]> expectedImage = new ArrayList<>();

        if (laserBehaviour.isFullLaser()) {
            expectedImage = laserBehaviour.getImage();
            assertEquals(19, expectedImage.get(0)[0]);
        } else if (laserBehaviour.getStart()) {
            expectedImage = laserBehaviour.getImage();
            assertEquals(16, expectedImage.get(0)[0]);
        } else {
            expectedImage = laserBehaviour.getImage();
            assertEquals(17, expectedImage.get(0)[0]);
        }
    }
}
