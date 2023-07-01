package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowBoardMovingMessageTest {
    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1, position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2, position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userDTO2 = new UserDTO("Player2", "pw", "ml");

    private final PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);
    private final PlayerDTO playerDTO2 = new PlayerDTO(robotDTO2, userDTO2);
    private final List<PlayerDTO> playerDTOList = new ArrayList<>();

    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        ShowBoardMovingMessage message = new ShowBoardMovingMessage(lobbyID, playerDTOList);

        int result = message.getLobbyID();

        Assertions.assertEquals(lobbyID, result);
    }

    @Test
    public void testGetPlayersDTO() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        ShowBoardMovingMessage message = new ShowBoardMovingMessage(123, playerDTOList);

        List<PlayerDTO> result = message.getPlayersDTO();

        Assertions.assertEquals(playerDTOList, result);
    }

    @Test
    public void testGetPlayersDTONull() {
        ShowBoardMovingMessage message = new ShowBoardMovingMessage(123, null);

        List<PlayerDTO> result = message.getPlayersDTO();

        Assertions.assertNull(result);
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
