package de.uol.swp.client.lobby.event;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * * Event used to show the Lobby window
 *
 * In order to show the Lobby window using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.SceneManager
 * @since 2022-15-11
 *
 */
public class ShowLobbyViewEvent {

    private final LobbyDTO lobby;
    private final UserDTO user;

    public ShowLobbyViewEvent(LobbyDTO lobby, UserDTO user) {
        this.lobby = lobby;
        this.user = user;
    }

    public LobbyDTO getLobby() {
        return lobby;
    }

    public UserDTO getUser() {
        return user;
    }
}
