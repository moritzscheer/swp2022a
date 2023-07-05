package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class StartGameRequestTest {

    private final UserDTO loggedInUser = new UserDTO("Player1", "pw", "ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", loggedInUser, "pw", false, chatID, false);

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

    @Test
    public void testGetLobbyID() {
        int numberBots = 2;
        int checkpoints = 4;
        StartGameRequest request = new StartGameRequest(lobbyDTO, numberBots, checkpoints);

        int result = request.getLobbyID();

        Assertions.assertEquals(lobbyDTO.getLobbyID(), result);
    }

    @Test
    public void testGetNumberBots() {
        int numberBots = 2;
        int checkpoints = 4;
        StartGameRequest request = new StartGameRequest(lobbyDTO, numberBots, checkpoints);

        int result = request.getNumberBots();

        Assertions.assertEquals(numberBots, result);
    }
}
