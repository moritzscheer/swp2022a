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

    public ShowAllPlayersCardsMessage(Map<UserDTO, CardDTO> userDTOCardDTOMap, int lobbyID) {
        this.userDTOCardDTOMap = userDTOCardDTOMap;
        this.lobbyID = lobbyID;
    }

    public Map<UserDTO, CardDTO> getUserDTOCardDTOMap() {
        return userDTOCardDTOMap;
    }

    public int getLobbyID() {
        return lobbyID;
    }
}
