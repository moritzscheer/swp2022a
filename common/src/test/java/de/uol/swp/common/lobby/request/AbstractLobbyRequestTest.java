package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");
    private final UserDTO newUser = new UserDTO("Player2", "pw", "ml");

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyName and userDTO
     * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {

        String name = "LobbyName";

        AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);

        Assertions.assertEquals(name, request.getName());
        Assertions.assertEquals(user, request.getUser());
    }

    /**
     * Tests the setName method
     *
     * @author WKempel
     * @result The setter should set the correct lobbyName
     * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testSetName() {

        String name = "LobbyName";

        AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);

        String newName = "NewLobbyName";
        request.setName(newName);

        Assertions.assertEquals(newName, request.getName());
    }

    /**
     * Tests the setUser method
     *
     * @author WKempel
     * @result The setter should set the correct newUser
     * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testSetUser() {

        String name = "LobbyName";

        AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);

        request.setUser(newUser);

        Assertions.assertEquals(newUser, request.getUser());
    }

    /**
     * Tests the constructor with null as value for the name
     *
     * @author WKempel
     * @result The constructor should throw an IllegalArgumentException
     * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
     * @since 2023-06-16
     */
    @Test
    public void testConstructorWithNullName() {

        String name = null;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    AbstractLobbyRequest request = new AbstractLobbyRequest(name, user);
                });
    }

    /**
     * Tests the constructor with null as value for the user
     *
     * @author WKempel
     * @result The constructor should throw an IllegalArgumentException
     * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
     * @since 2023-06-16
     */
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
