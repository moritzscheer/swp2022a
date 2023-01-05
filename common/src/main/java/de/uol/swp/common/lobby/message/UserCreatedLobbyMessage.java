package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

public class UserCreatedLobbyMessage extends AbstractLobbyMessage {

    private final LobbyDTO lobby;

    /**
     * Constructor
     *
     * This constructor generates a new Lobby Object with their password
     * variable set to an empty String.
     *
     * @param lobby LobbyDTO containing all the information of the lobby
     * @param user User containing the user
     * @author Moritz Scheer
     * @since 2022-11-29
     */
    public UserCreatedLobbyMessage(LobbyDTO lobby, UserDTO user) {
        super(lobby.getName(), user);
        this.lobby = lobby.createWithoutPassword(lobby);
    }

    /**
     * Getter for the list of users currently logged in
     *
     * @return list of users currently logged in
     * @author Moritz Scheer
     * @since 2022-11-29
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
