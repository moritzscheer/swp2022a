package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.response.AbstractLobbyResponse;

public class GetMapDataResponse extends AbstractLobbyResponse {
    private final int[][][][] boardImageIds;
    private final LobbyDTO lobby;

    /**
     * constructor
     *
     * @param lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataResponse(int[][][][] boardImageIds, LobbyDTO lobby ) {
        this.boardImageIds = boardImageIds;
        this.lobby = lobby;
    }

    /** Getter for the board image
     *
     * @return Image ids for the game board
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public int[][][][] getBoardImageIds() {
        return boardImageIds;
    }

    public int getLobbyID() {
        return lobby.getLobbyID();
    }

    public LobbyDTO getLobby() {
        return lobby;
    }
}
