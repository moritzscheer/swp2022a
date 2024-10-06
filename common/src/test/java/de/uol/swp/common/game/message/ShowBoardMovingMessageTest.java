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

    /**
     * Tests the getter of the LobbyID
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an
     * @result The method should return true and the correct lobbyID if the player is in the list
     * @see de.uol.swp.common.game.message.ShowBoardMovingMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        ShowBoardMovingMessage message = new ShowBoardMovingMessage(lobbyID, playerDTOList);

        int result = message.getLobbyID();

        Assertions.assertEquals(lobbyID, result);
    }

    /**
     * Tests the getPlayerDTO method
     *
     * @author WKempel
     * @result The method should return true and the correct playerDTO if the player is in the list
     * @see de.uol.swp.common.game.message.ShowBoardMovingMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetPlayersDTO() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        ShowBoardMovingMessage message = new ShowBoardMovingMessage(123, playerDTOList);

        List<PlayerDTO> result = message.getPlayersDTO();

        Assertions.assertEquals(playerDTOList, result);
    }

    /**
     * Tests the getPlayerDTO method with null as value
     *
     * @author WKempel
     * @result The method should return null
     * @see de.uol.swp.common.game.message.ShowBoardMovingMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetPlayersDTONull() {
        ShowBoardMovingMessage message = new ShowBoardMovingMessage(123, null);

        List<PlayerDTO> result = message.getPlayersDTO();

        Assertions.assertNull(result);
    }

    /**
     * Tests the getLobbyID with a negative value
     *
     * @author WKempel
     * @result The method should throw an IllegalArgumentException
     * @see de.uol.swp.common.game.message.ShowBoardMovingMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
