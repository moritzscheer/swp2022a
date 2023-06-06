package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.response.AbstractLobbyResponse;

public class GetMapDataResponse extends AbstractLobbyResponse {
    private final BlockDTO[][] boardDTOs;
    private final LobbyDTO lobby;
    private final Position checkPoint1Position;

    /**
     * constructor
     *
     * @param lobby
     * @param checkPoint1Position
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataResponse(BlockDTO[][] boardDTOs, LobbyDTO lobby, Position checkPoint1Position) {
        this.boardDTOs = boardDTOs;
        this.lobby = lobby;
        this.checkPoint1Position = checkPoint1Position;
    }

    /** Getter for the board image
     *
     * @return Image ids for the game board
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public BlockDTO[][] getBoardImageIds() {
        return boardDTOs;
    }

    public int getLobbyID() {
        return lobby.getLobbyID();
    }

    public LobbyDTO getLobby() {
        return lobby;
    }

    public Position getCheckPoint1Position() {
        return checkPoint1Position;
    }
}
