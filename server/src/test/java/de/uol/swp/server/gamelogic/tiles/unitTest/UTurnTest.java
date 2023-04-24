package de.uol.swp.server.gamelogic.tiles.unitTest;

import static junit.framework.TestCase.assertEquals;

import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.UTurn;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import org.junit.Test;

public class UTurnTest {

    private Position position = new Position(0, 0);

    private Robot robot = new Robot("Robot", position, true, null);

    /**
     * Test the robot changed the direction from north to south
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.UTurn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-04-05
     */
    @Test
    public void testUTunFromNorthToSouth() {
        UTurn uTurn = new UTurn();
        robot.setDirection(CardinalDirection.North);
        uTurn.uTurn(robot);
        assertEquals(CardinalDirection.South, robot.getDirection());
    }
    /**
     * Test the robot changed the direction from south to north
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.UTurn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-04-05
     */
    @Test
    public void testUTurnFromSouthToNorth() {
        UTurn uTurn = new UTurn();
        robot.setDirection(CardinalDirection.South);
        uTurn.uTurn(robot);
        assertEquals(CardinalDirection.North, robot.getDirection());
    }
    /**
     * Test the robot changed the direction from west to east
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.UTurn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-04-05
     */
    @Test
    public void testUTurnFromWestToEast() {
        UTurn uTurn = new UTurn();
        robot.setDirection(CardinalDirection.West);
        uTurn.uTurn(robot);
        assertEquals(CardinalDirection.East, robot.getDirection());
    }
    /**
     * Test the robot changed the direction from east to west
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.UTurn
     * @see de.uol.swp.server.gamelogic.Robot
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-04-05
     */
    @Test
    public void testUTurnFromEastToWest() {
        UTurn uTurn = new UTurn();
        robot.setDirection(CardinalDirection.East);
        uTurn.uTurn(robot);
        assertEquals(CardinalDirection.West, robot.getDirection());
    }
}
