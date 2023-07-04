package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.user.UserDTO;

import java.util.List;

/**
 * Event used to submit the cards of a players
 *
 * @author Maria Andrade
 * @since 2023-05-19
 */
public class SubmitCardsEvent {

    private final int lobbyID;
    private final UserDTO loggedInUser;
    private final List<CardDTO> cardDTOS;

    /**
     * constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public SubmitCardsEvent(int lobbyID, UserDTO loggedInUser, List<CardDTO> cardDTOS) {
        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
        this.cardDTOS = cardDTOS;
    }

    /**
     * Getter Method for lobbyID
     *
     * @author Maria Andrade
     * @return the lobby id
     * @since 2023-05-19
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Getter Method for current logged-in user
     *
     * @author Maria Andrade
     * @return the current logged-in user
     * @since 2023-05-19
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Getter Method for cardDTOS
     *
     * @author Maria Andrade
     * @return the cardDTOS
     * @since 2023-05-19
     */
    public List<CardDTO> getCardDTOS() {
        return cardDTOS;
    }
}
