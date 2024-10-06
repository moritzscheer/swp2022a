package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAllPlayerCardsMessageTest {

    /**
     * Tests the getUserDTOCardDTOMap method
     *
     * @author WKempel
     * @result The method should return the correct values
     * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetUserDTOCardDTOMap() {
        Map<UserDTO, CardDTO> userDTOCardDTOMap = new HashMap<>();
        ShowAllPlayersCardsMessage message = new ShowAllPlayersCardsMessage(userDTOCardDTOMap, 123);

        Map<UserDTO, CardDTO> result = message.getUserDTOCardDTOMap();

        Assertions.assertEquals(userDTOCardDTOMap, result);
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The method should return the correct lobbyID
     * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        ShowAllPlayersCardsMessage message =
                new ShowAllPlayersCardsMessage(new HashMap<>(), lobbyID);

        int result = message.getLobbyID();

        Assertions.assertEquals(lobbyID, result);
    }

    /**
     * Tests the getLobbyID method with a negative lobbyID value
     *
     * @author WKempel
     * @result The method should throw an IllegalArgumentException
     * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }

    /**
     * Tests the getUserDTOCardDTOMap method with a null value
     *
     * @author WKempel
     * @result The method should return null
     * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetUserDTOCardDTOMapIsNull() {
        ShowAllPlayersCardsMessage message = new ShowAllPlayersCardsMessage(null, 123);

        Map<UserDTO, CardDTO> result = message.getUserDTOCardDTOMap();

        Assertions.assertNull(result);
    }
}
