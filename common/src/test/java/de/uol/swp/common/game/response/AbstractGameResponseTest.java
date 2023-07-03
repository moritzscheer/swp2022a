package de.uol.swp.common.game.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class AbstractGameResponseTest {

    protected int gameID = 1;

    /**
     * Tests the constructor and the getGameID method
     *
     * @author WKempel
     * @result The method should return the correct gameID
     * @see de.uol.swp.common.game.response.AbstractGameResponse
     * @since 2023-06-27
     */
    @Test
    public void testConstructorAndGetGameID() {
        AbstractGameResponse abstractGameResponse = new AbstractGameResponse(gameID) {};

        assertEquals(gameID, abstractGameResponse.getGameID());
    }

    /**
     * Tests the setGameID method
     *
     * @author WKempel
     * @result The method should set the correct gameID
     * @see de.uol.swp.common.game.response.AbstractGameResponse
     * @since 2023-06-27
     */
    @Test
    public void testSetGameID() {
        AbstractGameResponse abstractGameResponse = new AbstractGameResponse(gameID) {};

        abstractGameResponse.setGameID(2);
        assertEquals(2, abstractGameResponse.getGameID());
    }

    /**
     * Tests the equals method
     *
     * @result The method should return true
     * @see de.uol.swp.common.game.response.AbstractGameResponse
     * @since 2023-06-27
     */
    @Test
    public void testNotEquals() {
        AbstractGameResponse response = new AbstractGameResponse(gameID) {};
        AbstractGameResponse otherResponse = new AbstractGameResponse(gameID) {};
        assertNotEquals(response, otherResponse);
    }

    /**
     * Tests the hashCode method
     *
     * @author WKempel
     * @result The method should return the same hashCode
     * @see de.uol.swp.common.game.response.AbstractGameResponse
     * @since 2023-06-27
     */
    @Test
    public void testHashCode() {
        AbstractGameResponse response = new AbstractGameResponse(gameID) {};
        AbstractGameResponse otherResponse = new AbstractGameResponse(gameID) {};
        assertEquals(response.hashCode(), otherResponse.hashCode());
    }
}
