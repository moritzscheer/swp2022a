package de.uol.swp.common.game.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoundIsOverMessageTest {

    @Test
    public void testConstructorAndGetters() {
        int lobbyID = 123;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID);

        Assertions.assertEquals(lobbyID, message.getLobbyID());
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID);

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
