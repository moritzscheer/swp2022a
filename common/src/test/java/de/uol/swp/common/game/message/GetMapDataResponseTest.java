package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetMapDataResponseTest {

    UserDTO userTest = new UserDTO("Test","pw","mail");

    @Test
    void constructor_ShouldSetCorrectValues() {
        BlockDTO[][] boardDTOs = new BlockDTO[12][12];
        LobbyDTO lobby = new LobbyDTO(123,"TestLobby",userTest,"pw",false,null);
        Position checkPoint1Position = new Position(6, 6);

        GetMapDataResponse response = new GetMapDataResponse(boardDTOs, lobby, checkPoint1Position);

        assertEquals(boardDTOs, response.getBoardImageIds());
        assertEquals(lobby.getLobbyID(), response.getLobbyID());
        assertEquals(lobby, response.getLobby());
        assertEquals(checkPoint1Position, response.getCheckPoint1Position());
    }

    @Test
    void constructor_ShouldCreateNonNullInstance() {
        BlockDTO[][] boardDTOs = new BlockDTO[12][12];
        LobbyDTO lobby = new LobbyDTO(123,"TestLobby",userTest,"pw",false,null);
        Position checkPoint1Position = new Position(6, 6);

        GetMapDataResponse response = new GetMapDataResponse(boardDTOs, lobby, checkPoint1Position);

        assertNotNull(response);
    }
}
