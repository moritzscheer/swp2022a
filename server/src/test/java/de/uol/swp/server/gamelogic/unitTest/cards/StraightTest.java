package de.uol.swp.server.gamelogic.unitTest.cards;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Straight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StraightTest {

    private final Position position = new Position(3, 0);
    private final CardinalDirection direction = CardinalDirection.North;
    private final Robot robot = new Robot(123, position, direction);

    /**
     * Tests getMoves method
     *
     * @author WKempel
     * @result The getMoves method should return the correct value and true if the number of moves
     *     are correct
     * @see de.uol.swp.server.gamelogic.cards.Straight
     * @since 2023-06-21
     */
    @Test
    public void testGetMoves() {
        Straight straight = new Straight(2);
        Assertions.assertEquals(2, straight.getMoves());
    }

    /**
     * Tests the move method
     *
     * @author WKempel
     * @result The move method should move the robot to the correct position
     * @see de.uol.swp.server.gamelogic.cards.Straight
     * @since 2023-06-21
     */
    @Test
    public void testMove() {
        Straight straight = new Straight(3);
        straight.move(robot, 3);
        Assertions.assertEquals(3, robot.getPosition().x);
    }
}
