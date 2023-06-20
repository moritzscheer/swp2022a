package de.uol.swp.common.game.request;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.message.AbstractRequestMessage;
import de.uol.swp.common.user.UserDTO;

import java.util.List;

/**
 * Request sent to the server when the user requests to send his chosen cards
 *
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Ole Zimmermann & Maria Eduarda Costa Leite Andrade
 * @since 2023-04-25
 */
public class SubmitCardsRequest extends AbstractRequestMessage {
    private static final long serialVersionUID = -5910486927806761643L;
    private final List<CardDTO> cardDTO;
    private final int lobbyID;
    private final UserDTO loggedInUser;

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    public SubmitCardsRequest(int lobbyID, UserDTO userDTO, List<CardDTO> cardDTOS) {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Lobby can not be null");
        }
        if (userDTO == null) {
            throw new NullPointerException("User can not be null");
        }
        if (cardDTOS == null) {
            throw new NullPointerException("CardDTO can not be null");
        }
        this.lobbyID = lobbyID;
        this.loggedInUser = userDTO;
        this.cardDTO = cardDTOS;
    }

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    public List<CardDTO> getCardDTOs() {
        return cardDTO;
    }

    @Override
    public boolean authorizationNeeded() {
        return true;
    }

    /**
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
