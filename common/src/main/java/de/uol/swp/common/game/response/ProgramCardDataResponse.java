package de.uol.swp.common.game.response;

import de.uol.swp.common.game.Game;
import de.uol.swp.common.game.dto.CardDTO;

/**
 * Response sent to the Client with all the cards data in it
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-01
 */
public class ProgramCardDataResponse extends AbstractGameResponse {
    private final CardDTO[] cards;
    private final Integer playerID;
    private final Integer gameID;

    /**
     * Constructor
     *
     * @param game the game to which this player belong
     * @param cards cards that were assigned to a player
     * @param playerID player that is in this game
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public ProgramCardDataResponse(Game game, CardDTO[] cards, Integer playerID) {
        super(game.getGameID());
        this.cards = cards;
        this.playerID = playerID;
        this.gameID = game.getGameID();
    }

    /**
     * Getter for the cards of a player
     *
     * @return CardDTO containing the card's data
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public CardDTO[] getAssignedProgramCards() {
        return this.cards;
    }

    /**
     * Getter for the player variable
     *
     * @return LobbyDTO object of the lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getPlayerID() {
        return this.playerID;
    }
}
