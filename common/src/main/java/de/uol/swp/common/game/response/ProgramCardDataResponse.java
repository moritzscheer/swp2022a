package de.uol.swp.common.game.response;

import de.uol.swp.common.game.dto.CardDTO;

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
    private int freeCards;
    private final int lobbyID;

    /**
     * Constructor
     *
     * @param cards cards that were assigned to a player
     * @param lobbyID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public ProgramCardDataResponse(List<CardDTO> cards, int freeCards, int lobbyID) {
        super();
        this.cards = cards;
        this.freeCards = freeCards;
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
     * Getter for the lockedCards variable
     *
     * @return lockedCards number of locked
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-10
     */
    public int getFreeCards() {
        return freeCards;
    }
}
