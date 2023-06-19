package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.user.UserDTO;

import java.util.List;

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

    public int getLobbyID() {
        return lobbyID;
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }

    public List<CardDTO> getCardDTOS() {
        return cardDTOS;
    }
}
