package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

import java.util.List;

/**
 * Message to distribute new cards and reset view for players
 *
 * @author Maxim Erden
 * @since 2023-06-06
 */
public class RoundIsOverMessage extends AbstractLobbyMessage {
    private final int lobbyID;

    private final List<PlayerDTO> respawnRobots;
    /**
     * Constructor
     *
     * @author Maxim Erden
     * @param lobbyID integer
     * @param respawnRobots List of PlayerDTO
     * @since 2023-06-06
     */
    public RoundIsOverMessage(int lobbyID, List<PlayerDTO> respawnRobots) {
        this.lobbyID = lobbyID;
        this.respawnRobots = respawnRobots;
    }
    /**
     * Getter for the lobby id
     *
     * @author Waldemar Kempel
     * @return lobby id as integer
     * @since 2023-06-14
     */
    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
    /**
     * Getter for the respawned robots
     *
     * @author Maria Andrade
     * @return list of playerDTO
     * @since 2023-06-14
     */
    public List<PlayerDTO> getRespawnRobots() {
        return respawnRobots;
    }
}
