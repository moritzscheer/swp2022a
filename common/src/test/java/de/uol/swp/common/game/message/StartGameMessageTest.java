package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.GameDTO;
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
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", userDTO, "pw", false, chatID);

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The method should return true and the correct lobbyID if the StartGameMessage has the
     *     correct lobbyID, lobbyDTO and gameDTO
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @see de.uol.swp.common.user.UserDTO
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyID() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);

        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        Integer result = message.getLobbyID();

        assertEquals(lobbyDTO.getLobbyID(), result);
    }

    /**
     * Tests the getLobby method
     *
     * @author WKempel
     * @result The method should return true and the correct lobby if the StartGameMessage has the
     *     correct lobbyID, lobbyDTO and gameDTO
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-14
     */
    @Test
    public void testGetLobby() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        LobbyDTO result = message.getLobby();

        assertEquals(lobbyDTO, result);
    }

    /**
     * Tests the getGame method
     *
     * @author WKempel
     * @result The method should return true and the correct game if the StartGameMessage has the
     *     correct lobbyID, lobbyDTO and gameDTO
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-14
     */
    @Test
    public void testGetGame() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        GameDTO result = message.getGame();

        assertEquals(game, result);
    }

    /**
     * Tests the constructor with null as value for the lobbyID
     *
     * @author WKempel
     * @result The method should throw a NullPointerException if the lobbyID is null
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @since 2023-06-14
     */
    @Test
    public void testConstructorWithNullLobby() {
        LobbyDTO lobby = null;
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new StartGameMessage(lobby.getLobbyID(), lobby, game);
                });
    }

    /**
     * Tests the constructor with null as value for the game
     *
     * @author WKempel
     * @result The method should throw a NullPointerException if the game is null
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-14
     */
    @Test
    public void testConstructorWithNullGame() {
        GameDTO game = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);
                });
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true if the StartGameMessage has the correct lobbyID,
     *     lobbyDTO, gameDTO and these are equal
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-14
     */
    @Test
    public void testEquals() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message1 = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        playerDTOList2.add(playerDTO);
        playerDTOList2.add(playerDTO2);
        GameDTO game2 = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message2 = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game2);

        assertEquals(message1, message2);
    }

    /**
     * Tests the equals method with the same instance
     *
     * @author WKempel
     * @result The method should return true if the StartGameMessage has the correct lobbyID,
     *     lobbyDTO, gameDTO and this is the same instance
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-14
     */
    @Test
    public void testEqualsSameInstance() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        assertEquals(message, message);
    }

    /**
     * Tests the equals method with a null object
     *
     * @author WKempel
     * @result The method should return false if the StartGameMessage has the correct lobbyID,
     *     lobbyDTO, gameDTO and the object is null
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @since 2023-06-14
     */
    @Test
    public void testEqualsNullObject() {
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message = new StartGameMessage(lobbyDTO.getLobbyID(), lobbyDTO, game);

        Assertions.assertNotEquals(message, null);
    }

    /**
     * Tests the getLobbyID method with negative value
     *
     * @author WKempel
     * @result throws IllegalArgumentException
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @see de.uol.swp.common.game.dto.GameDTO
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        LobbyDTO lobby = new LobbyDTO(lobbyID, "testLobby", userDTO, "pw", false, chatID);
        playerDTOList.add(playerDTO);
        playerDTOList.add(playerDTO2);
        GameDTO game = new GameDTO(playerDTOList, blockDTOS);
        StartGameMessage message = new StartGameMessage(lobby.getLobbyID(), lobby, game);

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
