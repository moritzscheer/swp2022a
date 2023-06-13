package de.uol.swp.common.game;

import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDTOTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1,position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2,position, CardinalDirection.North);
    private final RobotDTO robotDTO3 = new RobotDTO(3,position, CardinalDirection.North);
    private final RobotDTO robotDTO4 = new RobotDTO(4,position, CardinalDirection.North);
    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");
    private final UserDTO userDTO2 = new UserDTO("Player2","pw","ml2");
    private final UserDTO userDTO3 = new UserDTO("Player3","pw","ml3");
    private final UserDTO userDTO4 = new UserDTO("Player4","pw","ml4");

    @Test
    void getPlayersTest() {
        List<PlayerDTO> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new PlayerDTO(robotDTO,userDTO));
        expectedPlayers.add(new PlayerDTO(robotDTO2,userDTO2));
        GameDTO gameDTO = new GameDTO(expectedPlayers);

        List<PlayerDTO> actualPlayers = gameDTO.getPlayers();

        assertEquals(expectedPlayers, actualPlayers);
    }

    @Test
    void setPlayersTest() {
        List<PlayerDTO> players = new ArrayList<>();
        players.add(new PlayerDTO(robotDTO,userDTO));
        players.add(new PlayerDTO(robotDTO2,userDTO2));
        GameDTO gameDTO = new GameDTO(players);

        List<PlayerDTO> newPlayers = new ArrayList<>();
        newPlayers.add(new PlayerDTO(robotDTO3,userDTO3));
        newPlayers.add(new PlayerDTO(robotDTO4,userDTO4));
        gameDTO.setPlayers(newPlayers);

        List<PlayerDTO> updatedPlayers = gameDTO.getPlayers();

        assertEquals(gameDTO.getPlayers(), updatedPlayers);
    }

    @Test
    void constructorTest() {
        List<PlayerDTO> players = new ArrayList<>();
        players.add(new PlayerDTO(robotDTO,userDTO));
        players.add(new PlayerDTO(robotDTO2,userDTO2));

        GameDTO gameDTO = new GameDTO(players);

        assertNotNull(gameDTO);
        assertEquals(players, gameDTO.getPlayers());
    }

    @Test
    void setPlayersNullTest() {
        List<PlayerDTO> players = new ArrayList<>();
        players.add(new PlayerDTO(robotDTO,userDTO));
        players.add(new PlayerDTO(robotDTO2,userDTO2));

        GameDTO gameDTO = new GameDTO(players);

        gameDTO.setPlayers(null);

        assertNull(gameDTO.getPlayers());
    }


}
