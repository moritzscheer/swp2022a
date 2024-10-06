package de.uol.swp.common.game.request;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.message.AbstractRequestMessage;
import de.uol.swp.common.user.UserDTO;

import java.util.List;

/**
 * Request sent to the server when the user requests to send his chosen cards
 *
 * @author Ole Zimmermann & Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @since 2023-04-25
 */
public class SubmitCardsRequest extends AbstractRequestMessage {
    private static final long serialVersionUID = -5910486927806761643L;
    private final List<CardDTO> cardDTO;
    private final int lobbyID;
    private final UserDTO loggedInUser;

    /**
     * Constructor
     *
     * @param lobbyID integer of the lobby
     * @param userDTO UserDTO of the user
     * @param cardDTOS List of card DTOs
     * @author Waldemar Kempel, Maria Andrade
     * @since 2023-05-19
     */
    public SubmitCardsRequest(int lobbyID, UserDTO userDTO, List<CardDTO> cardDTOS) {
        if (lobbyID < 0) throw new IllegalArgumentException("Lobby can not be null");
        if (userDTO == null) throw new NullPointerException("User can not be null");
        if (cardDTOS == null) throw new NullPointerException("CardDTO can not be null");
        this.lobbyID = lobbyID;
        this.loggedInUser = userDTO;
        this.cardDTO = cardDTOS;
    }

    /**
     * Getter for the card DTOs
     *
     * @return list of card DTOs
     * @author Maria Andrade
     * @since 2023-05-19
     */
    public List<CardDTO> getCardDTOs() {
        return cardDTO;
    }

    @Override
    public boolean authorizationNeeded() {
        return true;
    }

    /**
     * Getter for the lobby id
     *
     * @return lobby id as integer
     * @author Maria Andrade
     * @since 2023-05-19
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Getter for the logged in user
     *
     * @return UserDTO of the user who is logged in
     * @author Maria Andrade
     * @since 2023-05-13
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
