package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;

import java.util.Map;

/**
 * Message sent by the server to show all cards of all players of the current program step
 *
 * @see AbstractLobbyMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-20
 */
public class ShowAllPlayersCardsMessage extends AbstractLobbyMessage {
    private final Map<UserDTO, CardDTO> userDTOCardDTOMap;
    private final int lobbyID;
    /**
     * Constructor
     *
     * @param userDTOCardDTOMap Map of User and card DTO
     * @param lobbyID integer
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-20
     */
    public ShowAllPlayersCardsMessage(Map<UserDTO, CardDTO> userDTOCardDTOMap, int lobbyID) {
        this.userDTOCardDTOMap = userDTOCardDTOMap;
        this.lobbyID = lobbyID;
    }
    /**
     * Getter for the user and card DTO map
     *
     * @return map of user and card DTO
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-20
     */
    public Map<UserDTO, CardDTO> getUserDTOCardDTOMap() {
        return userDTOCardDTOMap;
    }
    /**
     * Getter for the lobby id
     *
     * @return lobby id as integer
     * @author Maria Eduarda Costa Leite Andrade, Waldemar Kempel
     * @exception IllegalArgumentException if lobby id is negative
     * @since 2023-05-20
     */
    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
}
