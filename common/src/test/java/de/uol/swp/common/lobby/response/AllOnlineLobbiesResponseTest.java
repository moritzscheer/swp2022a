package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllOnlineLobbiesResponseTest {

    private final UUID uuid = UUID.randomUUID();
    private final UserDTO user = new UserDTO("Player1","pw","ml");
    private final UserDTO user2 = new UserDTO("Player2","pw2","ml2");
    private final LobbyDTO lobby1 = new LobbyDTO(123,"lobbyTest", user, "pw", true, uuid);
    private final LobbyDTO lobby2 = new LobbyDTO(123,"lobbyTest2", user2, "pw", true, uuid);


    @Test
    public void testDefaultConstructor() {
        // Arrange
        AllOnlineLobbiesResponse response = new AllOnlineLobbiesResponse();

        // Assert
        Assertions.assertTrue(response.getLobbies().isEmpty());
    }

    @Test
    public void testConstructorWithCollection() {

        List<LobbyDTO> lobbyCollection = new ArrayList<>();
        lobbyCollection.add(lobby1);
        lobbyCollection.add(lobby2);

        AllOnlineLobbiesResponse response = new AllOnlineLobbiesResponse(lobbyCollection);

        Assertions.assertEquals(lobbyCollection.size(), response.getLobbies().size());
        Assertions.assertFalse(response.getLobbies().containsAll(lobbyCollection));
    }

   @Test
    public void testConstructorWithCollectionNull() {

        Assertions.assertThrows(NullPointerException.class, () -> new AllOnlineLobbiesResponse(null));
    }

}
