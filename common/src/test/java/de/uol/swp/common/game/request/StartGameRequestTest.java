package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class StartGameRequestTest {

    private final UserDTO loggedInUser = new UserDTO("Player1", "pw", "ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", loggedInUser, "pw", false, chatID, false);

    /**
     * Tests the constructor with null as lobby
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNullLobby() {
        lobbyDTO = null;
        int numberBots = 2;
        int checkpoints = 4;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new StartGameRequest(lobbyDTO, numberBots, checkpoints);
                });
    }

    /**
     * Tests the constructor with a negative number of bots
     *
     * @author WKempel
     * @result The constructor should throw a IllegalArgumentException
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNegativeNumberBots() {
        int numberBots = -1;
        int checkpoints = -1;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new StartGameRequest(lobbyDTO, numberBots, checkpoints);
                });
    }

    /**
     * Tests the constructor with a negative number of checkpoints
     *
     * @author WKempel
     * @result The constructor should throw a IllegalArgumentException
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @since 2023-06-27
     */
    @Test
    public void testConstructorWithNegativeNumberCheckpoints() {
        int numberBots = -1;
        int checkpoints = -1;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new StartGameRequest(lobbyDTO, numberBots, checkpoints);
                });
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The method should return the lobbyID
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetLobbyID() {
        int numberBots = 2;
        int checkpoints = 4;
        StartGameRequest request = new StartGameRequest(lobbyDTO, numberBots, checkpoints);

        int result = request.getLobbyID();

        Assertions.assertEquals(lobbyDTO.getLobbyID(), result);
    }

    /**
     * Tests the getNumberBots method
     *
     * @author WKempel
     * @result The method should return the number of bots
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetNumberBots() {
        int numberBots = 2;
        int checkpoints = 4;
        StartGameRequest request = new StartGameRequest(lobbyDTO, numberBots, checkpoints);

        int result = request.getNumberBots();

        Assertions.assertEquals(numberBots, result);
    }

    /**
     * Tests the getNumberCheckpoints method
     *
     * @author WKempel
     * @result The method should return the number of checkpoints
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @since 2023-06-27
     */
    @Test
    public void testGetNumberCheckpoints() {
        int numberBots = 2;
        int checkpoints = 4;
        StartGameRequest request = new StartGameRequest(lobbyDTO, numberBots, checkpoints);

        int result = request.getNumberCheckpoints();

        Assertions.assertEquals(checkpoints, result);
    }
}
