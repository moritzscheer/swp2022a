package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.LaserBehaviour;
import de.uol.swp.server.gamelogic.tiles.WallBehaviour;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import org.junit.Before;
import org.junit.Test;

/**
 * Test LaserBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-03-13
 */
public class LaserBehaviourTest {

    private static final Position pos1 = new Position(1, 1);
    private static final Position pos2 = new Position(1, 2);
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
        robots[0] = new Robot("", pos1, true, CardinalDirection.East);
        robots[1] = new Robot("", pos2, true, CardinalDirection.East);
        robots[2] = new Robot("", pos3, true, CardinalDirection.East);
        behaviours1[0] =
                new LaserBehaviour(robots, board, activeInProgramSteps, pos1, laserDir, laserBeam);
        behaviours3[0] = new WallBehaviour(robots, board, pos3, wallDirection);
        behaviours4[0] =
                new LaserBehaviour(robots, board, activeInProgramSteps, pos1, laserDir, laserBeam);
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
    public void laserDamageRobot() {
        // robot 1 in same block as laser
        assertEquals(robots[0].getPosition(), behaviours1[0].getBlockPos());
        int beforeDamage = robots[0].getDamageToken();
        ((LaserBehaviour) behaviours1[0]).onLaserStage(1);
        int afterDamage = robots[0].getDamageToken();
        assertEquals(((LaserBehaviour) behaviours1[0]).getLaserBeam(), afterDamage - beforeDamage);
    }

    /**
     * Test not damage second robot, because first was damaged
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.LaserBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-03-13
     */
    @Test
    public void notDamageSecondRobot() {
        assertEquals(robots[0].getPosition(), behaviours1[0].getBlockPos());
        assertEquals(robots[1].getPosition(), pos2);
        int beforeDamage = robots[1].getDamageToken();
        ((LaserBehaviour) behaviours1[0]).onLaserStage(0);
        int afterDamage = robots[1].getDamageToken();
        assertEquals(0, beforeDamage - afterDamage);
    }

    /**
     * Test not damage second robot, because there is a wall
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.LaserBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-03-13
     */
    @Test
    public void notDamageSecondRobotWall() {
        // robot in same block as wall
        assertEquals(robots[2].getPosition(), behaviours3[0].getBlockPos());
        int beforeDamage = robots[2].getDamageToken();
        ((LaserBehaviour) behaviours4[0]).onLaserStage(0);
        int afterDamage = robots[2].getDamageToken();
        assertEquals(0, beforeDamage - afterDamage);
    }
}
