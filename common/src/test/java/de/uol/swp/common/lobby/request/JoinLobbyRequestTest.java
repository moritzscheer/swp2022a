package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JoinLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyName,lobbyID, user and password
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        String password = "secret";

        JoinLobbyRequest request = new JoinLobbyRequest(lobbyID, lobbyName, user, password);

        Assertions.assertEquals(lobbyID, request.getLobbyID());
        Assertions.assertEquals(lobbyName, request.getName());
        Assertions.assertEquals(user, request.getUser());
        Assertions.assertEquals(password, request.getPassword());
    }

    /**
     * Tests the getPassword method
     *
     * @author WKempel
     * @result The getter should return the correct password
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testGetPassword() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        String password = "secret";

        JoinLobbyRequest request = new JoinLobbyRequest(lobbyID, lobbyName, user, password);

        String retrievedPassword = request.getPassword();

        Assertions.assertEquals(password, retrievedPassword);
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The getter should return the correct lobbyID
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testGetLobbyID() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        String password = "secret";

        JoinLobbyRequest request = new JoinLobbyRequest(lobbyID, lobbyName, user, password);

        Integer retrievedLobbyID = request.getLobbyID();

        Assertions.assertEquals(lobbyID, retrievedLobbyID);
    }
}
