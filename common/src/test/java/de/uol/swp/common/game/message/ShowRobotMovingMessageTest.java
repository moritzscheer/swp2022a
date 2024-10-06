package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ShowRobotMovingMessageTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1, position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");

    private final PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an
     * @result The method should return true and the correct lobbyID if the player is in the list
     * @see de.uol.swp.common.game.message.ShowRobotMovingMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        ShowRobotMovingMessage message = new ShowRobotMovingMessage(lobbyID, playerDTO);

        int result = message.getLobbyID();

        Assertions.assertEquals(lobbyID, result);
    }

    /**
     * Tests the getPlayerDTO method
     *
     * @author WKempel
     * @result The method should return true and the correct playerDTO if the player is in the list
     * @see de.uol.swp.common.game.message.ShowRobotMovingMessage
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-14
     */
    @Test
    public void testGetPlayerDTO() {
        int lobbyID = 123;
        ShowRobotMovingMessage message = new ShowRobotMovingMessage(lobbyID, playerDTO);

        PlayerDTO result = message.getPlayerDTO();

        Assertions.assertEquals(playerDTO, result);
    }

    /**
     * Tests the getLobbyID method with a negative lobbyID
     *
     * @author WKempel
     * @result The method should throw an IllegalArgumentException
     * @see de.uol.swp.common.game.message.ShowRobotMovingMessage
     * @see de.uol.swp.common.game.message.ShowRobotMovingMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
