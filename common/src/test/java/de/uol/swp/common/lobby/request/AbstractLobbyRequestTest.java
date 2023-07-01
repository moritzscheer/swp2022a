package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");
    private final UserDTO newUser = new UserDTO("Player2", "pw", "ml");

    @Test
    public void testConstructorAndGetters() {

        String name = "LobbyName";

        AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);

        Assertions.assertEquals(name, request.getName());
        Assertions.assertEquals(user, request.getUser());
    }

    @Test
    public void testSetName() {

        String name = "LobbyName";

        AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);

        String newName = "NewLobbyName";
        request.setName(newName);

        Assertions.assertEquals(newName, request.getName());
    }

    @Test
    public void testSetUser() {

        String name = "LobbyName";

        AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);

        request.setUser(newUser);

        Assertions.assertEquals(newUser, request.getUser());
    }

    @Test
    public void testConstructorWithNullName() {

        String name = null;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);
                });
    }

    @Test
    public void testConstructorWithNullUser() {

        UserDTO user = null;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    AbstractLobbyRequest request = new AbstractLobbyRequest("LobbyName", user);
                });
    }
}
