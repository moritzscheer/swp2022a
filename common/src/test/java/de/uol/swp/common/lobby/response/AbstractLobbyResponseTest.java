package de.uol.swp.common.lobby.response;

import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractLobbyResponseTest {

    private final UserDTO user = new UserDTO("Player1","pw","ml");

    @Test
    public void testGettersAndSetters() {

        String name = "Test Lobby";

        AbstractLobbyResponse response = new AbstractLobbyResponse();
        response.setName(name);
        response.setUser(user);

        Assertions.assertEquals(name, response.getName());
        Assertions.assertEquals(user, response.getUser());
    }

    @Test
    public void testGettersAndSettersNull() {

        AbstractLobbyResponse response = new AbstractLobbyResponse();
        Assertions.assertThrows(NullPointerException.class, () -> response.setName(null));
        Assertions.assertThrows(NullPointerException.class, () -> response.setUser(null));
    }
}
