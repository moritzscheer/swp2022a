package de.uol.swp.common.game.response;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.lobby.dto.LobbyDTO;

import java.util.List;

/**
 * Response sent to the Client with all the cards data in it
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-01
 */
public class ProgramCardDataResponse extends AbstractGameResponse {
    private final List<CardDTO> cards;

    private final int lobbyID;
    //private final Integer playerID;

    /**
     * Constructor
     *
     * @param cards    cards that were assigned to a player
     * @param lobbyID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public ProgramCardDataResponse(List<CardDTO> cards, int lobbyID) {
        super();
        this.cards = cards;
        //this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the cards of a player
     *
     * @return CardDTO containing the card's data
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public List<CardDTO> getAssignedProgramCards() {
        return this.cards;
    }

    public int getLobbyID() {
        return this.lobbyID;
    }

    /**
     * Getter for the player variable
     *
     * @return LobbyDTO object of the lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    //public Integer getPlayerID() {
//        return this.playerID;
//    }
}
