package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.response.AbstractLobbyResponse;

public class GetMapDataResponse extends AbstractLobbyResponse {
    private final Integer gameID;
    private final int[][][][] boardImageIds;
    private final LobbyDTO lobby;

    /**
     * constructor
     *
     * @param gameID Integer containing the ID of the game
     * @param lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataResponse(Integer gameID, int[][][][] boardImageIds, LobbyDTO lobby ) {
        this.gameID = gameID;
        this.boardImageIds = boardImageIds;
        this.lobby = lobby;
    }

    /**
     * Getter for the gameID variable
     *
     * @return Integer containing the gameID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public Integer getGameID() {
        return gameID;
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
