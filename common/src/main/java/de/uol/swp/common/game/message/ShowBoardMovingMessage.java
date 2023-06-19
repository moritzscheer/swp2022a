package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

import java.util.List;

// TODO: maybe implement all movements together for the board, instead of one for each player
public class ShowBoardMovingMessage extends AbstractLobbyMessage {
    private final int lobbyID;
    // current position and directions and users dto are within playersDTO
    private final List<PlayerDTO> playersDTO;

    public ShowBoardMovingMessage(int lobbyID, List<PlayerDTO> playersDTO) {
        this.lobbyID = lobbyID;
        this.playersDTO = playersDTO;
    }

    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }

    public List<PlayerDTO> getPlayersDTO() {
        return playersDTO;
    }
}
