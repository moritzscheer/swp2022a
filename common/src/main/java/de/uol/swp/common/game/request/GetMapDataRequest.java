package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * Request sent to the server when the user requests the cards
 *
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-06
 */
public class GetMapDataRequest extends AbstractGameRequest {

    /**
     * constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public GetMapDataRequest(LobbyDTO lobby) {
        this.lobby = lobby;
    }
}
