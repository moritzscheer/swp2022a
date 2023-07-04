package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameDTOTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1, position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2, position, CardinalDirection.North);
    private final RobotDTO robotDTO3 = new RobotDTO(3, position, CardinalDirection.North);
    private final RobotDTO robotDTO4 = new RobotDTO(4, position, CardinalDirection.North);
    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userDTO2 = new UserDTO("Player2", "pw", "ml2");
    private final UserDTO userDTO3 = new UserDTO("Player3", "pw", "ml3");
    private final UserDTO userDTO4 = new UserDTO("Player4", "pw", "ml4");

    /**
     * Tests the getPlayers method
     *
     * @author WKempel
     * @result The method should return the players
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-13
     */
    @Test
    public void testGetPlayersTest() {
        List<PlayerDTO> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new PlayerDTO(robotDTO, userDTO));
        expectedPlayers.add(new PlayerDTO(robotDTO2, userDTO2));
        GameDTO gameDTO = new GameDTO(expectedPlayers, null);

        List<PlayerDTO> actualPlayers = gameDTO.getPlayers();

        assertEquals(expectedPlayers, actualPlayers);
    }

    /**
     * Tests the setPlayers method
     *
     * @author WKempel
     * @result The method should set the correct players
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-13
     */
    @Test
    public void testSetPlayersTest() {
        List<PlayerDTO> players = new ArrayList<>();
        players.add(new PlayerDTO(robotDTO, userDTO));
        players.add(new PlayerDTO(robotDTO2, userDTO2));
        GameDTO gameDTO = new GameDTO(players, null);

        List<PlayerDTO> newPlayers = new ArrayList<>();
        newPlayers.add(new PlayerDTO(robotDTO3, userDTO3));
        newPlayers.add(new PlayerDTO(robotDTO4, userDTO4));
        gameDTO.setPlayers(newPlayers);

        List<PlayerDTO> updatedPlayers = gameDTO.getPlayers();

        assertEquals(gameDTO.getPlayers(), updatedPlayers);
    }

    /**
     * Tests the constructor
     *
     * @author WKempel
     * @result The gameDTO should not be null
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-13
     */
    @Test
    public void testConstructorTest() {
        List<PlayerDTO> players = new ArrayList<>();
        players.add(new PlayerDTO(robotDTO, userDTO));
        players.add(new PlayerDTO(robotDTO2, userDTO2));

        GameDTO gameDTO = new GameDTO(players, null);

        assertNotNull(gameDTO);
        assertEquals(players, gameDTO.getPlayers());
    }

    /**
     * Tests the setPlayers method with null
     *
     * @result The method should set the players to null
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-13
     */
    @Test
    public void testSetPlayersNullTest() {
        List<PlayerDTO> players = new ArrayList<>();
        players.add(new PlayerDTO(robotDTO, userDTO));
        players.add(new PlayerDTO(robotDTO2, userDTO2));

        GameDTO gameDTO = new GameDTO(players, null);

        gameDTO.setPlayers(null);

        assertNull(gameDTO.getPlayers());
    }
}
