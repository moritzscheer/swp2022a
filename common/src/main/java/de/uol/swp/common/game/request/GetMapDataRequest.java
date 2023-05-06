package de.uol.swp.common.game.request;

/**
 * Request sent to the server when the user requests the cards
 *
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-06
 */
public class GetMapDataRequest extends AbstractGameRequest{
    private final Integer gameID;

    /**
     * constructor
     *
     * @param gameID Integer containing the ID of the game
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataRequest(Integer gameID) {
        this.gameID = gameID;
    }

    /**
     * Getter for the gameID variable
     *
     * @return Integer containing the gameID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getGameID() {
        return gameID;
    }


}
