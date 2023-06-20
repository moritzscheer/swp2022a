package de.uol.swp.common.lobby.response;

import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.user.UserDTO;

import java.util.Objects;

/**
 * Base class of all lobby response messages. Basic handling of lobby data.
 *
 * @see de.uol.swp.common.user.User
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Marco Grawunder
 * @since 2022-11-17
 */
public class AbstractLobbyResponse extends AbstractResponseMessage {

    String name;
    UserDTO user;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2022-11-16
     */
    public AbstractLobbyResponse() {}

    /**
     * Constructor
     *
     * @param name name of the lobby
     * @param user user responsible for the creation of this message
     * @since 2022-11-16
     */
    public AbstractLobbyResponse(String name, UserDTO user) {
        if (name == null) throw new NullPointerException("Name can not be null");
        if (user == null) throw new NullPointerException("User can not be null");
        this.name = name;
        this.user = user;
    }

    /**
     * Getter for the name variable
     *
     * @return String containing the lobby's name
     * @since 2022-11-16
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name variable
     *
     * @param name String containing the lobby's name
     * @since 2022-11-16
     */
    public void setName(String name) {
        if (name == null) throw new NullPointerException("Name can not be null");
        this.name = name;
    }

    /**
     * Getter for the user variable
     *
     * @return User responsible for the creation of this message
     * @since 2022-11-16
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Setter for the user variable
     *
     * @param user User responsible for the creation of this message
     * @since 2019-10-08
     */
    public void setUser(UserDTO user) {
        if (user == null) throw new NullPointerException("User can not be null");
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLobbyResponse that = (AbstractLobbyResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}
