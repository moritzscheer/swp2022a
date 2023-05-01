package de.uol.swp.common.game.response;

import de.uol.swp.common.game.Game;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.lobby.dto.LobbyDTO;

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
    private final PlayerDTO player;

    /**
     * Constructor
     *
     * @param game the game to which this player belong
     * @param lobby The lobby who created the game
     * @param cards cards that were assigned to a player
     * @param player player that is in this game
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public ProgramCardDataResponse(Game game, LobbyDTO lobby, CardDTO[] cards, PlayerDTO player) {
        super(game.getName(), lobby);
        this.cards = cards;
        this.player = player;
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
    public PlayerDTO getPlayer() {
        return this.player;
    }
}
