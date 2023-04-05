package de.uol.swp.server.gamelogic.tiles.unitTest;

import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.UTurn;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UTurnTest {

    private Position position = new Position(0,0);

    private Robot robot = new Robot("Robot",position,true,null);

    @Test
    public void testUTunFromNorthToSouth() {
        UTurn uTurn = new UTurn();
        robot.setDirection(CardinalDirection.North);
        uTurn.uTurn(robot,true);
        assertEquals(CardinalDirection.South,robot.getDirection());
    }
}
