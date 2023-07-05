package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * This event class is used by the LobbyGameManagement to tell the GameService to create a new
 * request to the server
 *
 * @author Maria Andrade
 * @since 2023-05-18
 */
public class RequestMapDataEvent {
    private final LobbyDTO lobbyDTO;

    /**
     * Constructor to assign lobbyDTO the RequestMapDataEvent
     *
     * @author Maria Andrade
     * @since 2023-05-15
     */
    public RequestMapDataEvent(LobbyDTO lobbyDTO) {
        this.lobbyDTO = lobbyDTO;
    }

    /**
     * Getter method to return the LobbyDTO
     *
     * @author Maria Andrade
     * @return the lobby DTO
     * @since 2023-05-15
     */
    public LobbyDTO getLobbyDTO() {
        return lobbyDTO;
    }
}
