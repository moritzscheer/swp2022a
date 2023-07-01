package de.uol.swp.common.game.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RoundIsOverMessageTest {

    @Test
    public void testConstructorAndGetters() {
        int lobbyID = 123;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertEquals(lobbyID, message.getLobbyID());
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }

    @Test
    public void testEqualsAndHashCode() {
        int lobbyID = 123;
        RoundIsOverMessage message1 = new RoundIsOverMessage(lobbyID, new ArrayList<>());
        RoundIsOverMessage message2 = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertEquals(message1, message2);
        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
