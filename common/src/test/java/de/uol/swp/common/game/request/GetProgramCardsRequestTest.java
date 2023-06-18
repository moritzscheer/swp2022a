package de.uol.swp.common.game.request;

import de.uol.swp.common.game.request.GetProgramCardsRequest;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GetProgramCardsRequestTest {

    private final UserDTO loggedInUser = new UserDTO("Player1","pw","ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO = new LobbyDTO(123,"testLobby", loggedInUser,"pw",false,chatID);

    @Test
    public void testGetLobbyID() {
        GetProgramCardsRequest request = new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);

        int result = request.getLobbyID();

        Assertions.assertEquals(lobbyDTO.getLobbyID(), result);
    }

    @Test
    public void testGetLoggedInUser() {
        UserDTO loggedInUser = this.loggedInUser;
        GetProgramCardsRequest request = new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);

        UserDTO result = request.getLoggedInUser();

        Assertions.assertEquals(loggedInUser, result);
    }

    @Test
    public void testConstructorWithNegativeLobbyID() {
          UserDTO loggedInUser = new UserDTO("Player1","pw","ml");
          UUID chatID = UUID.randomUUID();
          LobbyDTO lobbyDTO = new LobbyDTO(-1,"testLobby",loggedInUser,"pw",false,chatID);

        Assertions.assertThrows(NullPointerException.class, () -> {
            new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);
        });
    }

    @Test
    public void testConstructorWithNullLoggedInUser() {
        int lobbyID = 123;
        UserDTO loggedInUser = null;

        Assertions.assertThrows(NullPointerException.class, () -> {
            new GetProgramCardsRequest(lobbyID, loggedInUser);
        });
    }

    @Test
    public void testGetLoggedInUserNotNull() {
        GetProgramCardsRequest request = new GetProgramCardsRequest(lobbyDTO.getLobbyID(), loggedInUser);

        UserDTO result = request.getLoggedInUser();

        Assertions.assertNotNull(result);
    }
}
