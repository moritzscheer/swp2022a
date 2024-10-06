package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RobotTurnedOffMessageTest {

    private final UUID uuid = UUID.randomUUID();
    private final UserDTO turnedOffUser = new UserDTO("testUser", "pw", "ml");
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", turnedOffUser, "pw", true, uuid, false);

    /**
     * Tests the constructor and the getter
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @result The method should return the correct values
     * @see de.uol.swp.common.game.message.RobotTurnedOffMessage
     * @since 2023-06-14
     */
    @Test
    public void testConstructorAndGetter() {
        RobotTurnedOffMessage robotTurnedOffMessage =
                new RobotTurnedOffMessage(lobbyDTO.getLobbyID(), turnedOffUser);

        assertEquals(lobbyDTO.getLobbyID(), robotTurnedOffMessage.getLobbyID());
        assertEquals(turnedOffUser, robotTurnedOffMessage.getTurnedOffUser());
    }
}
