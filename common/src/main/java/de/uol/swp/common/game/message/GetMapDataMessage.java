package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

public class GetMapDataMessage extends AbstractLobbyMessage {
    private final Integer gameID;
    private final int lobbyID;
    private final int[][][][] boardImageIds;

    /**
     * constructor
     *
     * @param gameID Integer containing the ID of the game
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataMessage(Integer gameID, int[][][][] boardImageIds, int lobbyId) {
        this.gameID = gameID;
        this.boardImageIds = boardImageIds;
        this.lobbyID = lobbyId;
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
        return lobbyID;
    }
}
