package de.uol.swp.common.lobby.message;

import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

/**
 * A response, that the Lobby creation was successful
 *
 * This response is only sent to clients that previously sent a CreateLobbyRequest
 * that was executed successfully, otherwise an ExceptionMessage would be sent.
 *
 * @author Moritz Scheer
 * @since 2022-11-15
 */

public class LobbyCreatedResponse extends AbstractLobbyResponse {

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2022-11-16
     */
    public LobbyCreatedResponse() {
    }

    /**
     * Constructor
     *
     * @param name name of the lobby
     * @param owner User trying to create the lobby
     * @since 2022-11-16
     */
    public LobbyCreatedResponse(String name, UserDTO owner) {
        super(name, owner);
    }

    /**
     * Setter for the user variable
     *
     * @param owner  User trying to create the lobby
     * @since 2022-11-16
     */
    public void setOwner(UserDTO owner) {
        setUser(owner);
    }

    /**
     * Getter for the user variable
     *
     * @return User trying to create the lobby
     * @since 2022-11-16
     */
    public User getOwner() {
        return getUser();
    }
}
