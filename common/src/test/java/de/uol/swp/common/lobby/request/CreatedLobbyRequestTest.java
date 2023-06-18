package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreatedLobbyRequestTest {

    private final UserDTO owner = new UserDTO("Player1","pw","ml");
    private UserDTO tmp = new UserDTO("Player2","pw","ml");

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

    @Test
    public void testGetOwner() {
        String name = "LobbyName";
        Boolean multiplayer = true;
        String password = "secret";

        CreateLobbyRequest request = new CreateLobbyRequest(name, owner, multiplayer, password);
        tmp = (UserDTO) request.getOwner();

        Assertions.assertEquals(owner, tmp);
    }

    @Test
    public void testIsMultiplayer() {
        String name = "LobbyName";
        Boolean multiplayer = true;
        String password = "secret";

        CreateLobbyRequest request = new CreateLobbyRequest(name, owner, multiplayer, password);

        Boolean isMultiplayer = request.isMultiplayer();

        Assertions.assertEquals(multiplayer, isMultiplayer);
    }

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
