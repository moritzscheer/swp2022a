package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GetProgramCardsRequestTest {

    private final UserDTO loggedInUser = new UserDTO("Player1", "pw", "ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", loggedInUser, "pw", false, chatID);

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The method should return the correct lobbyID
     * @see de.uol.swp.common.game.request.GetProgramCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetLobbyID() {
        GetProgramCardsRequest request =
                new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);

        int result = request.getLobbyID();

        Assertions.assertEquals(lobbyDTO.getLobbyID(), result);
    }

    /**
     * Tests the getLoggedInUser method
     *
     * @author WKempel
     * @result The method should return the correct loggedInUser
     * @see de.uol.swp.common.game.request.GetProgramCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetLoggedInUser() {
        UserDTO loggedInUser = this.loggedInUser;
        GetProgramCardsRequest request =
                new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);

        UserDTO result = request.getLoggedInUser();

        Assertions.assertEquals(loggedInUser, result);
    }

    /**
     * Tests the constructor with a negative lobbyID
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.GetProgramCardsRequest
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @see de.uol.swp.common.user.UserDTO
     * @see java.util.UUID
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNegativeLobbyID() {
        UserDTO loggedInUser = new UserDTO("Player1", "pw", "ml");
        UUID chatID = UUID.randomUUID();
        LobbyDTO lobbyDTO = new LobbyDTO(-1, "testLobby", loggedInUser, "pw", false, chatID);

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);
                });
    }

    /**
     * Tests the constructor with a null as value for the loggedInUser
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.GetProgramCardsRequest
     * @see de.uol.swp.common.user.UserDTO
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNullLoggedInUser() {
        int lobbyID = 123;
        UserDTO loggedInUser = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new GetProgramCardsRequest(lobbyID, loggedInUser);
                });
    }

    /**
     * Tests the constructor with a null as value for the loggedInUser
     *
     * @author WKempel
     * @result The that the loggedInUser is not null
     * @see de.uol.swp.common.game.request.GetProgramCardsRequest
     * @see de.uol.swp.common.user.UserDTO
     * @since 2023-06-15
     */
    @Test
    public void testGetLoggedInUserNotNull() {
        GetProgramCardsRequest request =
                new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);

        UserDTO result = request.getLoggedInUser();

        Assertions.assertNotNull(result);
    }
}
