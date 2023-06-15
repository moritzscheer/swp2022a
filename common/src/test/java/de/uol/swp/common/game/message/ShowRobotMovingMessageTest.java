package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.game.message.RoundIsOverMessage;
import de.uol.swp.common.game.message.ShowRobotMovingMessage;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShowRobotMovingMessageTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1,position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2,position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");
    private final UserDTO userDTO2 = new UserDTO("Player2","pw","ml");

    private final PlayerDTO playerDTO = new PlayerDTO(robotDTO,userDTO);
    private final PlayerDTO playerDTO2 = new PlayerDTO(robotDTO2,userDTO2);

    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        ShowRobotMovingMessage message = new ShowRobotMovingMessage(lobbyID, playerDTO);

        int result = message.getLobbyID();

        Assertions.assertEquals(lobbyID, result);
    }

    @Test
    public void testGetPlayerDTO() {
        int lobbyID = 123;
        ShowRobotMovingMessage message = new ShowRobotMovingMessage(lobbyID, playerDTO);

        PlayerDTO result = message.getPlayerDTO();

        Assertions.assertEquals(playerDTO, result);
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID);

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }

}
