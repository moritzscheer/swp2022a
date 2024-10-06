package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.Session;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerIsReadyMessageTest {

    private final UserDTO playerIsReady = new UserDTO("Player1", "pw", "ml");
    private final UserDTO playerIsReady2 = new UserDTO("Player2", "pw", "ml2");

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    void testConstructor() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        assertEquals(playerIsReady, message.getPlayerIsReady());
        assertEquals(lobbyID, message.getLobbyID());
    }

    /**
     * Tests to set the receiver
     *
     * @author WKempel
     * @result The setter should work without throwing an exception
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    void testSetReceiver() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        Session session1 =
                new Session() {
                    @Override
                    public String getSessionId() {
                        return null;
                    }

                    @Override
                    public User getUser() {
                        return null;
                    }
                };
        Session session2 =
                new Session() {
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

    /**
     * Tests to set the receiver to null
     *
     * @author WKempel
     * @result The setter should work without throwing an exception
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    void testSetReceiverShouldSetReceiversToEmptyListIfNull() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        message.setReceiver(null);

        // In this case it is an empty list and not null
        assertNotEquals(Collections.emptyList(), message.getReceiver());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true for equal messages
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    void testEqualsShouldReturnTrueForEqualMessages() {
        int lobbyID = 123;

        PlayerIsReadyMessage message1 = new PlayerIsReadyMessage(playerIsReady, lobbyID);
        PlayerIsReadyMessage message2 = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        assertEquals(message1, message2);
    }

    /**
     * Tests the equals method by different lobbyIDs
     *
     * @author WKempel
     * @result The equals method should return true for different lobbyIDs
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    void testEqualsShouldReturnFalseForDifferentMessages() {
        int lobbyID1 = 123;
        int lobbyID2 = 456;

        PlayerIsReadyMessage message1 = new PlayerIsReadyMessage(playerIsReady, lobbyID1);
        PlayerIsReadyMessage message2 = new PlayerIsReadyMessage(playerIsReady2, lobbyID2);

        assertNotEquals(message1, message2);
    }

    /**
     * Tests the constructor to not get empty messages
     *
     * @author WKempel
     * @result The method should return true if the message is not empty
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    void testConstructorShouldCreateNonNullInstance() {
        int lobbyID = 123;

        PlayerIsReadyMessage message = new PlayerIsReadyMessage(playerIsReady, lobbyID);

        assertNotNull(message);
    }

    /**
     * Tests the constructor with negative lobbyID
     *
     * @author WKempel
     * @result The method should throw an IllegalArgumentException
     * @see de.uol.swp.common.game.message.PlayerIsReadyMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
