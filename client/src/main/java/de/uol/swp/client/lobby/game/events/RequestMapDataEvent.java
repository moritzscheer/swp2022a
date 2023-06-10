package de.uol.swp.client.lobby.game.events;

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

    public RequestMapDataEvent(LobbyDTO lobbyDTO) {

        this.lobbyDTO = lobbyDTO;
    }

    public LobbyDTO getLobbyDTO() {
        return lobbyDTO;
    }
}
