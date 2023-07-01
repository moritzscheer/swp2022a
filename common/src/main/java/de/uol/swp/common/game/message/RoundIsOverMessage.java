package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

import java.util.List;

/**
 * Message to distribute new cards and reset view for players
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-24
 */
public class RoundIsOverMessage extends AbstractLobbyMessage {
    private final int lobbyID;

    private final List<PlayerDTO> respawnRobots;

    public RoundIsOverMessage(int lobbyID, List<PlayerDTO> respawnRobots) {
        this.lobbyID = lobbyID;
        this.respawnRobots = respawnRobots;
    }

    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }

    public List<PlayerDTO> getRespawnRobots() {
        return respawnRobots;
    }
}
