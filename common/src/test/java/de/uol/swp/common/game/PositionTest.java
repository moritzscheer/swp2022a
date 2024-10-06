package de.uol.swp.common.game;

import de.uol.swp.common.game.enums.CardinalDirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {

    /**
     * Tests the translate method
     *
     * @author WKempel
     * @result The method should return the correct position
     * @see de.uol.swp.common.game.Position
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-15
     */
    @Test
    public void testTranslate() {
        Position position = new Position(0, 0);

        // Test translation in each cardinal direction
        Position eastPosition = Position.translate(position, CardinalDirection.East);
        Assertions.assertEquals(1, eastPosition.x);
        Assertions.assertEquals(0, eastPosition.y);

        Position westPosition = Position.translate(position, CardinalDirection.West);
        Assertions.assertEquals(-1, westPosition.x);
        Assertions.assertEquals(0, westPosition.y);

        Position northPosition = Position.translate(position, CardinalDirection.North);
        Assertions.assertEquals(0, northPosition.x);
        Assertions.assertEquals(-1, northPosition.y);

        Position southPosition = Position.translate(position, CardinalDirection.South);
        Assertions.assertEquals(0, southPosition.x);
        Assertions.assertEquals(1, southPosition.y);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return the correct boolean
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-15
     */
    @Test
    public void testEquals() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(0, 0);
        Position p3 = new Position(1, 0);

        Assertions.assertEquals(p1, p2);

        Assertions.assertNotEquals(p1, p3);
    }
}
