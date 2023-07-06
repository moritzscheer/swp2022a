package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StartGameMessageTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1, position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2, position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userDTO2 = new UserDTO("Player2", "pw", "ml");

    private final PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);
    private final PlayerDTO playerDTO2 = new PlayerDTO(robotDTO2, userDTO2);
    private final List<PlayerDTO> playerDTOList = new ArrayList<>();
    private final List<PlayerDTO> playerDTOList2 = new ArrayList<>();

    private final BlockDTO[][] blockDTOS = new BlockDTO[12][12];
    private final UUID chatID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", userDTO, "pw", false, chatID, false);

    @Test
    public void testGetLobbyID() {
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID());

        Integer result = message.getLobbyID();

        assertEquals(lobbyDTO.getLobbyID(), result);
    }

    @Test
    public void testEquals() {
        StartGameMessage message1 = new StartGameMessage(lobbyDTO.getLobbyID());
        StartGameMessage message2 = new StartGameMessage(lobbyDTO.getLobbyID());

        assertEquals(message1, message2);
    }

    @Test
    public void testEqualsSameInstance() {
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID());

        assertEquals(message, message);
    }

    @Test
    public void testEqualsNullObject() {
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID());

        Assertions.assertNotEquals(message, null);
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        LobbyDTO lobby = new LobbyDTO(lobbyID, "testLobby", userDTO, "pw", false, chatID, false);
        StartGameMessage message = new StartGameMessage(lobby.getLobbyID());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
