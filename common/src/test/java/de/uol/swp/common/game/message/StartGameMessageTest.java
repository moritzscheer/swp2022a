package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StartGameMessageTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1,position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2,position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");
    private final UserDTO userDTO2 = new UserDTO("Player2","pw","ml");

    private final PlayerDTO playerDTO = new PlayerDTO(robotDTO,userDTO);
    private final PlayerDTO playerDTO2 = new PlayerDTO(robotDTO2,userDTO2);
    private final List<PlayerDTO> playerDTOList = new ArrayList<>();
    private final List<PlayerDTO> playerDTOList2 = new ArrayList<>();
    private final UUID chatID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123,"testLobby",userDTO,"pw",false,chatID);

    @Test
    public void testGetLobbyID() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);

        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        Integer result = message.getLobbyID();

        Assertions.assertEquals(lobbyDTO.getLobbyID(), result);
    }

    @Test
    public void testGetLobby() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        LobbyDTO result = message.getLobby();

        Assertions.assertEquals(lobbyDTO, result);
    }

    @Test
    public void testGetGame() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        GameDTO result = message.getGame();

        Assertions.assertEquals(game, result);
    }

    @Test
    public void testConstructorWithNullLobby() {
        LobbyDTO lobby = null;
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);

        Assertions.assertThrows(NullPointerException.class, () -> {
            new StartGameMessage(lobby.getLobbyID(), lobby, game);
        });
    }

    @Test
    public void testConstructorWithNullGame() {
        GameDTO game = null;

        Assertions.assertThrows(NullPointerException.class, () -> {
            new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);
        });
    }

    @Test
    public void testEquals() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);
        StartGameMessage message1 = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        playerDTOList2.add(playerDTO);
        playerDTOList2.add(playerDTO2);
        GameDTO game2 = new GameDTO(playerDTOList);
        StartGameMessage message2 = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game2);

        Assertions.assertEquals(message1, message2);
    }

    @Test
    public void testEqualsSameInstance() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        Assertions.assertEquals(message, message);
    }

    @Test
    public void testEqualsNullObject() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        Assertions.assertNotEquals(message, null);
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        LobbyDTO lobby = new LobbyDTO(lobbyID,"testLobby",userDTO,"pw",false,chatID);
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList);
        StartGameMessage message = new StartGameMessage(lobby.getLobbyID(), lobby, game);

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
