package de.uol.swp.common.game.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AbstractGameResponseTest {

    protected int gameID = 1;

    @Test
    public void testConstructorAndGetGameID() {
        AbstractGameResponse abstractGameResponse = new AbstractGameResponse(gameID) {
        };

        assertEquals(gameID, abstractGameResponse.getGameID());
    }

    @Test
    public void testSetGameID() {
        AbstractGameResponse abstractGameResponse = new AbstractGameResponse(gameID) {
        };

        abstractGameResponse.setGameID(2);
        assertEquals(2, abstractGameResponse.getGameID());
    }

    @Test
    public void testNotEquals() {
        AbstractGameResponse response = new AbstractGameResponse(gameID) {
        };
        AbstractGameResponse otherResponse = new AbstractGameResponse(gameID) {
        };
        assertNotEquals(response, otherResponse);
    }

    @Test
    public void testHashCode() {
        AbstractGameResponse response = new AbstractGameResponse(gameID) {
        };
        AbstractGameResponse otherResponse = new AbstractGameResponse(gameID) {
        };
        assertEquals(response.hashCode(), otherResponse.hashCode());
    }

}
