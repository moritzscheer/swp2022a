package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/** This event class is used by the presenters to tell
 * the GameService to create a new request to the server
 *
 * @author Maria Andrade
 * @since 2023-05-16
 */
public class RequestStartGameEvent {

    private final LobbyDTO lobbyDTO;
    public RequestStartGameEvent(LobbyDTO lobbyDTO) {
        this.lobbyDTO = lobbyDTO;
    }

    public LobbyDTO getLobbyDTO() {
        return lobbyDTO;
    }
}
