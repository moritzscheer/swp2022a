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

public class RoundIsOverMessageTest {

    /**
     * Tests the constructor and getter
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an
     * @result The method should return the correct value
     * @see de.uol.swp.common.game.message.RoundIsOverMessage
     * @since 2023-06-14
     */
    @Test
    public void testConstructorAndGetters() {
        int lobbyID = 123;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertEquals(lobbyID, message.getLobbyID());
    }

    /**
     * Tests the constructor with a negative lobbyID
     *
     * @author WKempel
     * @result The constructor should throw an IllegalArgumentException
     * @see de.uol.swp.common.game.message.RoundIsOverMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }

    /**
     * Tests the equals method and the hashCode method
     *
     * @author WKempel
     * @result The equals methods should return true if the objects are the same
     * @see de.uol.swp.common.game.message.RoundIsOverMessage
     * @since 2023-06-18
     */
    @Test
    public void testEqualsAndHashCode() {
        int lobbyID = 123;
        RoundIsOverMessage message1 = new RoundIsOverMessage(lobbyID, new ArrayList<>());
        RoundIsOverMessage message2 = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertEquals(message1, message2);
        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }

    /**
     * Tests respawn robots
     *
     * @author Maria
     * @see de.uol.swp.common.game.message.RoundIsOverMessage
     * @since 2023-07-05
     */
    @Test
    public void testRespawnRobots() {
        int lobbyID = 123;
        RobotDTO robotDTO = new RobotDTO(1, new Position(1, 1), CardinalDirection.South);
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, new UserDTO("Maria", "mypass", ""));
        List<PlayerDTO> respawn = new ArrayList<>() {};
        respawn.add(playerDTO);

        RoundIsOverMessage message1 = new RoundIsOverMessage(lobbyID, respawn);
        RoundIsOverMessage message2 = new RoundIsOverMessage(lobbyID, respawn);

        Assertions.assertEquals(message1, message2);
        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
        Assertions.assertEquals(message1.getRespawnRobots(), message2.getRespawnRobots());
    }
}
