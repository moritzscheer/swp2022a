package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when a user wants to delete the current lobby
 *
 * @see de.uol.swp.common.lobby.message.AbstractLobbyRequest
 * @see de.uol.swp.common.user.User
 * @author Moritz Scheer
 * @since 2022-11-16
 */

public class DropLobbyRequest extends AbstractLobbyRequest{

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
    public DropLobbyRequest(String name, UserDTO owner) {
        super(name, owner);
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
}
