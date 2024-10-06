package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AbstractGameRequestTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", userDTO, "pw", false, chatID, false);
    private LobbyDTO lobbyDTO2 =
            new LobbyDTO(123, "testLobby2", userDTO, "pw", false, chatID, false);

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an
     * @result The method should return true if the lobby name and lobbyDTO is the same
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorAndGetters() {

        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);

        Assertions.assertEquals(lobbyDTO.getName(), request.getName());
        Assertions.assertEquals(lobbyDTO, request.getLobby());
    }

    /**
     * Tests the setters
     *
     * @author WKempel
     * @result The setters should work without throwing an exception
     * @result The method should set the correct values like the name and lobbyDTO
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testSetters() {

        String name = "Updated Game";
        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);

        request.setName(name);
        request.setLobby(lobbyDTO);

        Assertions.assertEquals(name, request.getName());
        Assertions.assertEquals(lobbyDTO, request.getLobby());
    }

    /**
     * Tests the constructor with null as value for the name
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNullName() {
        String name = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new AbstractGameRequest(name, lobbyDTO);
                });
    }

    /**
     * Tests the constructor with null as value for the lobbyDTO
     *
     * @author WKempel
     * @result The constructor should throw a IllegalArgumentException
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNullLobby() {
        String name = "Test Game";
        lobbyDTO = null;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new AbstractGameRequest(name, lobbyDTO);
                });
    }

    /**
     * Tests the setter with null as value for the name
     *
     * @author WKempel
     * @result The setter should throw a NullPointerException
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testSetterWithNullName() {
        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);
        String name = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    request.setName(name);
                });
    }

    /**
     * Tests the setter with null as value for the lobbyDTO
     *
     * @author WKempel
     * @result The setter should throw a NullPointerException
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testSetterWithNullLobby() {
        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);
        lobbyDTO = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    request.setLobby(lobbyDTO);
                });
    }

    /**
     * Tests the equals and hashCode method
     *
     * @author WKempel
     * @result The method should return true if the lobby name and lobbyDTO is the same
     * @result The method should return false if the lobby name and lobbyDTO is not the same
     * @see de.uol.swp.common.game.request.AbstractGameRequest
     * @since 2023-06-18
     */
    @Test
    public void testEqualsAndHashCode() {

        AbstractGameRequest request1 = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);
        AbstractGameRequest request2 = new AbstractGameRequest(lobbyDTO2.getName(), lobbyDTO2);

        Assertions.assertEquals(request1, request1);
        Assertions.assertEquals(request1.hashCode(), request1.hashCode());

        Assertions.assertNotEquals(request1, request2);
        Assertions.assertNotEquals(request1.hashCode(), request2.hashCode());
    }
}
