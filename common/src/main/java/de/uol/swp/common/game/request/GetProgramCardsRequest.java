package de.uol.swp.common.game.request;

import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when the user requests the cards
 *
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-01
 */
public class GetProgramCardsRequest extends AbstractGameRequest {
    private final int lobbyID;
    private final UserDTO loggedInUser;
    /**
     * constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public GetProgramCardsRequest(int lobbyID, UserDTO loggedInUser) {
        if(lobbyID < 0) {
            throw new NullPointerException("Lobby can not be null");
        }
        if(loggedInUser == null) {
            throw new NullPointerException("User can not be null");
        }

        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
