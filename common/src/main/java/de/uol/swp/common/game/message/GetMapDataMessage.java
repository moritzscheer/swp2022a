package de.uol.swp.common.game.message;

public class GetMapDataMessage {
    private final Integer gameID;
    private final int[][] boardImageIds; // TODO: fix how to send the information from the MapBuilder

    /**
     * constructor
     *
     * @param gameID Integer containing the ID of the game
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataMessage(Integer gameID, int[][] boardImageIds) {
        this.gameID = gameID;
        this.boardImageIds = boardImageIds;
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
    public int[][] getBoardImageIds() {
        return boardImageIds;
    }
}
