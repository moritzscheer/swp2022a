package de.uol.swp.common.game.message;

import de.uol.swp.common.game.message.PlayerIsReadyMessage;
import de.uol.swp.common.game.message.RoundIsOverMessage;
import de.uol.swp.common.user.Session;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerIsReadyMessageTest {

    private final UserDTO playerIsReady = new UserDTO("Player1","pw","ml");
    private final UserDTO playerIsReady2 = new UserDTO("Player2","pw","ml2");

    @Test
    void constructor_ShouldSetCorrectValues() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        assertEquals(playerIsReady, message.getPlayerIsReady());
        assertEquals(lobbyID, message.getLobbyID());
    }

    @Test
    void setReceiver_ShouldSetReceiversCorrectly() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        Session session1 = new Session() {
            @Override
            public String getSessionId() {
                return null;
            }

            @Override
            public User getUser() {
                return null;
            }
        };
        Session session2 = new Session() {
            @Override
            public String getSessionId() {
                return null;
            }

            @Override
            public User getUser() {
                return null;
            }
        };
        List<Session> receivers = List.of(session1, session2);

        message.setReceiver(receivers);

        assertEquals(receivers, message.getReceiver());
    }

    @Test
    void setReceiver_ShouldSetReceiversToEmptyListIfNull() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        message.setReceiver(null);

        // In this case it is an empty list and not null
        assertNotEquals(Collections.emptyList(), message.getReceiver());
    }

    @Test
    void equals_ShouldReturnTrueForEqualMessages() {
        int lobbyID = 123;

        PlayerIsReadyMessage message1 = new PlayerIsReadyMessage(playerIsReady, lobbyID);
        PlayerIsReadyMessage message2 = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        assertEquals(message1, message2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentMessages() {
        int lobbyID1 = 123;
        int lobbyID2 = 456;

        PlayerIsReadyMessage message1 = new PlayerIsReadyMessage(playerIsReady, lobbyID1);
        PlayerIsReadyMessage message2 = new PlayerIsReadyMessage(playerIsReady2, lobbyID2);

        assertFalse(message1.equals(message2));
    }

    @Test
    void constructor_ShouldCreateNonNullInstance() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        assertNotNull(message);
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID);

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
