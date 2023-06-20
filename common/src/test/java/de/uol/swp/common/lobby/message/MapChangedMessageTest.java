package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MapChangedMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userDTO2 = new UserDTO("Player2", "pw", "ml");
    private final int lobbyID = 123;

    @Test
    public void testConstructorAndGetters() {
        Map map = null;
        MapChangedMessage message =
                new MapChangedMessage(lobbyID, userDTO, (de.uol.swp.common.game.Map) map);

        Assertions.assertEquals(lobbyID, message.getLobbyID());
        Assertions.assertEquals(userDTO, message.getUser());
        Assertions.assertEquals(map, message.getMap());
    }

    @Test
    public void testEqualsAndHashCode() {
        int lobbyID1 = 123;
        Map map1 = null;
        MapChangedMessage message1 = new MapChangedMessage(lobbyID1, userDTO, (de.uol.swp.common.game.Map) map1);

        int lobbyID2 = 456;
        Map map2 = null;
        MapChangedMessage message2 = new MapChangedMessage(lobbyID2, userDTO2, (de.uol.swp.common.game.Map) map2);

        MapChangedMessage message3 = new MapChangedMessage(lobbyID1, userDTO, (de.uol.swp.common.game.Map) map1);

        Assertions.assertNotEquals(message1, message2);
        Assertions.assertEquals(message1, message3);

        Assertions.assertNotEquals(message1.hashCode(), message2.hashCode());
        Assertions.assertEquals(message1.hashCode(), message3.hashCode());
    }
}
