package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when a user wants to join a lobby
 *
 * @see AbstractLobbyRequest
 * @see de.uol.swp.common.user.User
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class LobbyJoinUserRequest extends AbstractLobbyRequest {

    private String password;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public LobbyJoinUserRequest() {
    }
    /**
     * Constructor
     *
     * @param name name of the lobby
     * @param user user who wants to join the lobby
     * @since 2019-10-08
     */
    public LobbyJoinUserRequest(String name, UserDTO user, String password) {
        super(name, user);
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
