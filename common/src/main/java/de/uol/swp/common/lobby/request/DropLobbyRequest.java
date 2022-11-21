package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when a user wants to delete the current lobby
 *
 * @see AbstractLobbyRequest
 * @see de.uol.swp.common.user.User
 * @author Moritz Scheer
 * @since 2022-11-16
 */

public class DropLobbyRequest extends AbstractLobbyRequest {

    private Boolean multiplayer;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public DropLobbyRequest() {
    }

    /**
     * Constructor
     *
     * @param name name of the lobby
     * @param owner User trying to create the lobby
     * @since 2019-10-08
     */
    public DropLobbyRequest(String name, UserDTO owner, Boolean multiplayer) {
        super(name, owner);
        this.multiplayer = multiplayer;
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
    public void setMultiplayer(Boolean isMultiplayer) {
        this.multiplayer = multiplayer;
    }

}
