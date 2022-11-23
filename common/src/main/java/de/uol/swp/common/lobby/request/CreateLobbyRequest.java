package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when a user wants to create a new lobby
 *
 * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
 * @see de.uol.swp.common.user.User
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class CreateLobbyRequest extends AbstractLobbyRequest {

    private Boolean multiplayer;
    private String password;       //passwort of the lobby

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public CreateLobbyRequest() {
    }

    /**
     * Constructor
     *
     * @param name name of the lobby
     * @param owner User trying to create the lobby
     * @since 2019-10-08
     */
    public CreateLobbyRequest(String name, UserDTO owner, Boolean multiplayer, String password) {
        super(name, owner);
        this.multiplayer = multiplayer;
        this.password = password;
    }

    /**
     * Setter for the user variable
     *
     * @param owner  User trying to create the lobby
     * @since 2019-10-08
     */
    public void setOwner(UserDTO owner) {
        setUser(owner);
    }

    /**
     * Getter for the user variable
     *
     * @return User trying to create the lobby
     * @since 2019-10-08
     */
    public User getOwner() {
        return getUser();
    }
    public Boolean isMultiplayer() {
        return multiplayer;
    }
    public String getPassword() {
        return password;
    }
}
