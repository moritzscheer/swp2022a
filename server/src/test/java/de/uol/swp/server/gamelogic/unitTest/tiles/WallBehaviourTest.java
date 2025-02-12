package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.*;
import de.uol.swp.server.gamelogic.tiles.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WallBehaviourTest {
    private static final Robot[] robots = new Robot[2];
    private static final Block[][] board = new Block[5][5];
    private static final AbstractTileBehaviour[] tileBehaviours = new AbstractTileBehaviour[13];

    private int[] activeInProgramSteps = new int[] {1, 2};

    @Before
    public void setUp() {
        robots[0] = new Robot(1, new Position(1, 1), CardinalDirection.East);
        robots[1] = new Robot(2, new Position(2, 1), CardinalDirection.East);

        tileBehaviours[0] =
                new WallBehaviour(
                        List.of(robots), board, new Position(1, 1), CardinalDirection.East);
        tileBehaviours[1] =
                new PusherBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 1),
                        new int[] {1},
                        CardinalDirection.East);
        tileBehaviours[2] =
                new WallBehaviour(
                        List.of(robots), board, new Position(1, 1), CardinalDirection.West);
        tileBehaviours[3] =
                new PusherBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 1),
                        new int[] {1},
                        CardinalDirection.West);
        tileBehaviours[4] =
                new WallBehaviour(
                        List.of(robots), board, new Position(1, 1), CardinalDirection.North);
        tileBehaviours[5] =
                new PusherBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 1),
                        new int[] {1},
                        CardinalDirection.North);
        tileBehaviours[6] =
                new WallBehaviour(
                        List.of(robots), board, new Position(1, 1), CardinalDirection.South);
        tileBehaviours[7] =
                new PusherBehaviour(
                        List.of(robots),
                        board,
                        new Position(1, 1),
                        new int[] {1},
                        CardinalDirection.South);
        tileBehaviours[8] =
                new ConveyorBeltBehaviour(
                        List.of(robots), board, new Position(1, 1), null, CardinalDirection.East);
        tileBehaviours[9] =
                new ConveyorBeltBehaviour(
                        List.of(robots), board, new Position(1, 1), null, CardinalDirection.West);
        tileBehaviours[10] =
                new ConveyorBeltBehaviour(
                        List.of(robots), board, new Position(1, 1), null, CardinalDirection.North);
        tileBehaviours[11] =
                new ConveyorBeltBehaviour(
                        List.of(robots), board, new Position(1, 1), null, CardinalDirection.South);
        tileBehaviours[12] =
                new LaserBehaviour(
                        List.of(robots),
                        board,
                        activeInProgramSteps,
                        new Position(1, 1),
                        CardinalDirection.East,
                        3,
                        true,
                        true);

        board[1][1] = new Block(tileBehaviours, "", new Position(1, 1));
        board[2][2] = new Block(tileBehaviours, "", new Position(2, 2));
    }

    /**
     * Tests if a robot is pushed in a wall
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @Test
    public void pushRobotInWallTest() {
        // robot in block 1,1 to be pushed
        // program step is 1
        assertTrue(tileBehaviours[0].getObstruction(CardinalDirection.East));
        assertTrue(tileBehaviours[2].getObstruction(CardinalDirection.West));
        assertTrue(tileBehaviours[4].getObstruction(CardinalDirection.North));
        assertTrue(tileBehaviours[6].getObstruction(CardinalDirection.South));
        // Pushes robot
        tileBehaviours[1].onPusherStage(1);
        tileBehaviours[3].onPusherStage(1);
        tileBehaviours[5].onPusherStage(1);
        tileBehaviours[7].onPusherStage(1);
        assertEquals(new Position(1, 1), robots[0].getPosition());
    }

    /**
     * Tests if a conveyor belt is in a wall
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @Test
    public void conveyorInWallTest() {
        // robot in block 1,1 to be pushed
        // program step is 1
        assertTrue(tileBehaviours[0].getObstruction(CardinalDirection.East));
        assertTrue(tileBehaviours[2].getObstruction(CardinalDirection.West));
        assertTrue(tileBehaviours[4].getObstruction(CardinalDirection.North));
        assertTrue(tileBehaviours[6].getObstruction(CardinalDirection.South));
        // Pushes robot
        tileBehaviours[8].onConveyorStage(1);
        tileBehaviours[9].onConveyorStage(1);
        tileBehaviours[10].onConveyorStage(1);
        tileBehaviours[11].onConveyorStage(1);
        assertEquals(new Position(1, 1), robots[0].getPosition());
    }

    /**
     * Tests if a robot get damage behind standing a wall
     *
     * @author WKempel
     * @since 2023-03-13
     */
    @Test
    public void notDamageRobotBehindWall() {
        // robot in same block as wall
        int beforeDamage = robots[1].getDamageToken();
        tileBehaviours[12].onLaserStage(0);
        int afterDamage = robots[1].getDamageToken();
        assertEquals(0, beforeDamage - afterDamage);
    }

    @Test
    public void testRobotMoveCard() {
        assertTrue(tileBehaviours[0].getObstruction(CardinalDirection.East));
        Position before = robots[0].getPosition();

        // movement by cards will be controlled by player
        robots[0].move(new Position(0, 1));
        assertEquals(robots[0].getPosition(), before);
    }

    /**
     * Tests the getImage method
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @Test
    public void testGetImage() {
        List<int[]> image = new ArrayList<>();
        image = tileBehaviours[0].getImage();
        assertEquals(1, image.get(0)[0]);
    }
}
