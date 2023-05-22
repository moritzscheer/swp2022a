package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.message.AbstractServerMessage;
import de.uol.swp.common.user.UserDTO;

import java.util.Map;

public class ShowAllPlayersCardsMessage extends AbstractServerMessage {
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
