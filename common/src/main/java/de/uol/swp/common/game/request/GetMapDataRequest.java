package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;

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
    public GetMapDataRequest(LobbyDTO lobby, Integer gameID) {
        this.lobby = lobby;
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
