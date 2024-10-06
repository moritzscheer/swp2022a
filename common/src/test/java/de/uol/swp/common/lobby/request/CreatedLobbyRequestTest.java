package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreatedLobbyRequestTest {

    private final UserDTO owner = new UserDTO("Player1", "pw", "ml");
    private UserDTO tmp = new UserDTO("Player2", "pw", "ml");

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyName, owner, multiplayer status and
     *     password
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {
        String name = "LobbyName";
        Boolean multiplayer = true;
        String password = "secret";

        CreateLobbyRequest request = new CreateLobbyRequest(name, owner, multiplayer, password);

        Assertions.assertEquals(name, request.getName());
        Assertions.assertEquals(owner, request.getOwner());
        Assertions.assertEquals(multiplayer, request.isMultiplayer());
        Assertions.assertEquals(password, request.getPassword());
    }

    /**
     * Tests the getOwner method
     *
     * @author WKempel
     * @result The getter should return the correct owner
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testGetOwner() {
        String name = "LobbyName";
        Boolean multiplayer = true;
        String password = "secret";

        CreateLobbyRequest request = new CreateLobbyRequest(name, owner, multiplayer, password);
        tmp = (UserDTO) request.getOwner();

        Assertions.assertEquals(owner, tmp);
    }

    /**
     * Tests the isMultiplayer method
     *
     * @author WKempel
     * @result The getter should return the correct multiplayer status
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testIsMultiplayer() {
        String name = "LobbyName";
        Boolean multiplayer = true;
        String password = "secret";

        CreateLobbyRequest request = new CreateLobbyRequest(name, owner, multiplayer, password);

        Boolean isMultiplayer = request.isMultiplayer();

        Assertions.assertEquals(multiplayer, isMultiplayer);
    }

    /**
     * Tests the getPassword method
     *
     * @author WKempel
     * @result The getter should return the correct password
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testGetPassword() {
        String name = "LobbyName";
        Boolean multiplayer = true;
        String password = "secret";

        CreateLobbyRequest request = new CreateLobbyRequest(name, owner, multiplayer, password);

        String retrievedPassword = request.getPassword();

        Assertions.assertEquals(password, retrievedPassword);
    }
}
