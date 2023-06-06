package de.uol.swp.client.lobby.lobby.event;

import de.uol.swp.common.user.UserDTO;

/** This event class is used by the presenters to tell
 * the LobbyService to create a new request to the server
 *
 * @author Maria Andrade
 * @since 2023-05-16
 */
public class CreateNewLobbyEvent {
    private final String name;
    private final UserDTO user;
    private final Boolean multiplayer;
    private final String password;
    public CreateNewLobbyEvent(String name, UserDTO user, Boolean multiplayer, String password) {
        this.name = name;
        this.user = user;
        this.multiplayer = multiplayer;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public UserDTO getUser() {
        return user;
    }

    public Boolean getMultiplayer() {
        return multiplayer;
    }

    public String getPassword() {
        return password;
    }
}
