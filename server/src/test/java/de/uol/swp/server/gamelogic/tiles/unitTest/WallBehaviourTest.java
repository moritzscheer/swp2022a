package de.uol.swp.server.gamelogic.tiles.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.server.gamelogic.*;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import org.junit.Before;
import org.junit.Test;

public class WallBehaviourTest {
    private static final Robot[] robots = new Robot[2];
    private static final Block[][] board = new Block[5][5];
    private static final AbstractTileBehaviour[] tileBehaviours = new AbstractTileBehaviour[13];

    @Before
    public void setUp() {
        robots[0] = new Robot("1", new Position(1, 1), true, CardinalDirection.East);
        robots[1] = new Robot("2", new Position(2, 1), true, CardinalDirection.East);

        tileBehaviours[0] =
                new WallBehaviour(robots, board, new Position(1, 1), CardinalDirection.East);
        tileBehaviours[1] =
                new PusherBehaviour(
                        robots, board, new Position(1, 1), new int[] {1}, CardinalDirection.East);
        tileBehaviours[2] =
                new WallBehaviour(robots, board, new Position(1, 1), CardinalDirection.West);
        tileBehaviours[3] =
                new PusherBehaviour(
                        robots, board, new Position(1, 1), new int[] {1}, CardinalDirection.West);
        tileBehaviours[4] =
                new WallBehaviour(robots, board, new Position(1, 1), CardinalDirection.North);
        tileBehaviours[5] =
                new PusherBehaviour(
                        robots, board, new Position(1, 1), new int[] {1}, CardinalDirection.North);
        tileBehaviours[6] =
                new WallBehaviour(robots, board, new Position(1, 1), CardinalDirection.South);
        tileBehaviours[7] =
                new PusherBehaviour(
                        robots, board, new Position(1, 1), new int[] {1}, CardinalDirection.South);
        tileBehaviours[8] =
                new ConveyorBeltBehaviour(
                        robots, board, new Position(1, 1), null, CardinalDirection.East);
        tileBehaviours[9] =
                new ConveyorBeltBehaviour(
                        robots, board, new Position(1, 1), null, CardinalDirection.West);
        tileBehaviours[10] =
                new ConveyorBeltBehaviour(
                        robots, board, new Position(1, 1), null, CardinalDirection.North);
        tileBehaviours[11] =
                new ConveyorBeltBehaviour(
                        robots, board, new Position(1, 1), null, CardinalDirection.South);
        tileBehaviours[12] =
                new LaserBehaviour(robots, board, new Position(1, 1), CardinalDirection.East, 3);

        board[1][1] = new Block(tileBehaviours, "", new Position(1, 1));
        board[2][2] = new Block(tileBehaviours, "", new Position(2, 2));
    }

    @Test
    public void pushRobotInWallTest() {
        // robot in block 1,1 to be pushed
        // program step is 1
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[0]).getObstruction(CardinalDirection.East));
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[2]).getObstruction(CardinalDirection.West));
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[4]).getObstruction(CardinalDirection.North));
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[6]).getObstruction(CardinalDirection.South));
        // Pushes robot
        ((PusherBehaviour) tileBehaviours[1]).OnPusherStage(1);
        ((PusherBehaviour) tileBehaviours[3]).OnPusherStage(1);
        ((PusherBehaviour) tileBehaviours[5]).OnPusherStage(1);
        ((PusherBehaviour) tileBehaviours[7]).OnPusherStage(1);
        assertEquals(new Position(1, 1), robots[0].getPosition());
    }

    @Test
    public void conveyorInWallTest() {
        // robot in block 1,1 to be pushed
        // program step is 1
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[0]).getObstruction(CardinalDirection.East));
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[2]).getObstruction(CardinalDirection.West));
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[4]).getObstruction(CardinalDirection.North));
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[6]).getObstruction(CardinalDirection.South));
        // Pushes robot
        ((ConveyorBeltBehaviour) tileBehaviours[8]).OnConveyorStage(1);
        ((ConveyorBeltBehaviour) tileBehaviours[9]).OnConveyorStage(1);
        ((ConveyorBeltBehaviour) tileBehaviours[10]).OnConveyorStage(1);
        ((ConveyorBeltBehaviour) tileBehaviours[11]).OnConveyorStage(1);
        assertEquals(new Position(1, 1), robots[0].getPosition());
    }

    @Test
    public void notDamageRobotBehindWall() {
        // robot in same block as wall
        int beforeDamage = robots[1].getDamageToken();
        ((LaserBehaviour) tileBehaviours[12]).damageRobot();
        int afterDamage = robots[1].getDamageToken();
        assertEquals(0, beforeDamage - afterDamage);
    }

    @Test
    public void robotMoveCard() {
        assertEquals(
                true, ((WallBehaviour) tileBehaviours[0]).getObstruction(CardinalDirection.East));
        Position before = robots[0].getPosition();
        robots[0].move(new Card[0]);
        assertEquals(robots[0].getPosition(), before);
    }
}
