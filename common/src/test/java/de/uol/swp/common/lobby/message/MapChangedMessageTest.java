package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MapChangedMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");
    private final int lobbyID = 123;

    @Test
    public void testConstructorAndGetters() {
        Map map = null;
        MapChangedMessage message = new MapChangedMessage(lobbyID, userDTO, (de.uol.swp.common.game.Map) map);

        Assertions.assertEquals(lobbyID, message.getLobbyID());
        Assertions.assertEquals(userDTO, message.getUser());
        Assertions.assertEquals(map, message.getMap());
    }
}
