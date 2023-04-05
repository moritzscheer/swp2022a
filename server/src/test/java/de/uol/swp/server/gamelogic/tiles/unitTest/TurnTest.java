package de.uol.swp.server.gamelogic.tiles.unitTest;

import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Direction;
import de.uol.swp.server.gamelogic.cards.Turn;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TurnTest {

    private Position position = new Position(0,0);

    private Robot robot = new Robot("Robot",position,true,null);

    /**
     * These following four tests implements the right turn
     */

    /**
     * Test the robot changed the direction from north to east
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromNorthToEast() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.North);
        turn.turn(robot, Direction.Right);
        assertEquals(CardinalDirection.East,robot.getDirection());
    }

    /**
     * Test the robot changed the direction from east to south
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromEastToSouth() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.East);
        turn.turn(robot, Direction.Right);
        assertEquals(CardinalDirection.South,robot.getDirection());
    }

    /**
     * Test the robot changed the direction from south to west
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromSouthToWest() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.South);
        turn.turn(robot, Direction.Right);
        assertEquals(CardinalDirection.West,robot.getDirection());
    }
    /**
     * Test the robot changed the direction from west to north
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromWestToNorth() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.West);
        turn.turn(robot, Direction.Right);
        assertEquals(CardinalDirection.North,robot.getDirection());
    }

    /**
     * These following four tests implements the left turn
     */

    /**
     * Test the robot changed the direction from north to west
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromNorthToWest() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.North);
        turn.turn(robot, Direction.Left);
        assertEquals(CardinalDirection.West,robot.getDirection());
    }

    /**
     * Test the robot changed the direction from west to south
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromWestToSouth() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.West);
        turn.turn(robot, Direction.Left);
        assertEquals(CardinalDirection.South,robot.getDirection());
    }

    /**
     * Test the robot changed the direction from south to east
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromSouthToEast() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.South);
        turn.turn(robot, Direction.Left);
        assertEquals(CardinalDirection.East,robot.getDirection());
    }

    /**
     * Test the robot changed the direction from east to north
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.Turn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @see de.uol.swp.server.gamelogic.cards
     * @since 2023-04-05
     */
    @Test
    public void testTurnFromEastToNorth() {
        Turn turn = new Turn();
        robot.setDirection(CardinalDirection.East);
        turn.turn(robot, Direction.Left);
        assertEquals(CardinalDirection.North,robot.getDirection());
    }
}
