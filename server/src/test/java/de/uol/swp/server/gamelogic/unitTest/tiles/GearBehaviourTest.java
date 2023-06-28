package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.GearBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test GearBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-02-20
 */
public class GearBehaviourTest {

    private static final Position pos1 = new Position(1, 1);
    private static final Position pos2 = new Position(1, 2);
    private static final Robot[] robots = new Robot[2];
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours2 = new AbstractTileBehaviour[1];
    private static final Block[][] board = new Block[1][2];

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot(1, pos1, CardinalDirection.East);
        robots[1] = new Robot(2, pos2, CardinalDirection.East);
        behaviours1[0] = new GearBehaviour(List.of(robots), board, pos1, true);
        behaviours2[0] = new GearBehaviour(List.of(robots), board, pos2, false);
        board[0][0] = new Block(behaviours1, "", pos1);
        board[0][1] = new Block(behaviours2, "", pos2);
    }

    /**
     * Test turn robot 1 clockwise (east -> south)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotClockwiseEastTest() {
        // robot dir = East
        robots[0].setDirection(CardinalDirection.East);
        assertEquals(CardinalDirection.East, robots[0].getDirection());
        behaviours1[0].onRotatorStage(1);
        assertEquals(CardinalDirection.South, robots[0].getDirection());
    }

    /**
     * Test turn robot 1 clockwise (south -> west)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotClockwiseSouthTest() {
        // robot dir = South
        robots[0].setDirection(CardinalDirection.South);
        assertEquals(CardinalDirection.South, robots[0].getDirection());
        behaviours1[0].onRotatorStage(1);
        assertEquals(CardinalDirection.West, robots[0].getDirection());
    }

    /**
     * Test turn robot 1 clockwise (west -> north)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotClockwiseWestTest() {
        // robot dir = West
        robots[0].setDirection(CardinalDirection.West);
        assertEquals(CardinalDirection.West, robots[0].getDirection());
        behaviours1[0].onRotatorStage(1);
        assertEquals(CardinalDirection.North, robots[0].getDirection());
    }

    /**
     * Test turn robot 1 clockwise (north -> east)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotClockwiseNorthTest() {
        // robot dir = North
        robots[0].setDirection(CardinalDirection.North);
        assertEquals(CardinalDirection.North, robots[0].getDirection());
        behaviours1[0].onRotatorStage(1);
        assertEquals(CardinalDirection.East, robots[0].getDirection());
    }

    /**
     * Test turn robot 2 counterclockwise (east -> north)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotCounterClockwiseEastTest() {
        // robot dir = East
        robots[1].setDirection(CardinalDirection.East);
        assertEquals(CardinalDirection.East, robots[1].getDirection());
        behaviours2[0].onRotatorStage(1);
        assertEquals(CardinalDirection.North, robots[1].getDirection());
    }

    /**
     * Test turn robot 2 counterclockwise (south -> east)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotCounterClockwiseSouthTest() {
        // robot dir = South
        robots[1].setDirection(CardinalDirection.South);
        assertEquals(CardinalDirection.South, robots[1].getDirection());
        behaviours2[0].onRotatorStage(1);
        assertEquals(CardinalDirection.East, robots[1].getDirection());
    }

    /**
     * Test turn robot 2 counterclockwise (west -> south)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotCounterClockwiseWestTest() {
        // robot dir = West
        robots[1].setDirection(CardinalDirection.West);
        assertEquals(CardinalDirection.West, robots[1].getDirection());
        behaviours2[0].onRotatorStage(1);
        assertEquals(CardinalDirection.South, robots[1].getDirection());
    }

    /**
     * Test turn robot 2 counterclockwise (north -> west)
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.GearBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-20
     */
    @Test
    public void turnRobotCounterClockwiseNorthTest() {
        // robot dir = North
        robots[1].setDirection(CardinalDirection.North);
        assertEquals(CardinalDirection.North, robots[1].getDirection());
        behaviours2[0].onRotatorStage(1);
        assertEquals(CardinalDirection.West, robots[1].getDirection());
    }
}
