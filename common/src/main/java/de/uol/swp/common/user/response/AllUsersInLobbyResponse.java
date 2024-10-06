package de.uol.swp.common.user.response;

import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Response message for the RetrieveAllUsersInLobbyResponse
 *
 * <p>This message gets sent to the client that sent an RetrieveAllUsersInLobbyRequest. It contains
 * a List with User objects of every user currently logged in to the lobby.
 *
 * @author Marco Grawunder
 * @see de.uol.swp.common.message.AbstractResponseMessage
 * @see de.uol.swp.common.user.request.RetrieveAllUsersInLobbyRequest
 * @see de.uol.swp.common.user.User
 * @since 2022-11-20
 */
public class AllUsersInLobbyResponse extends AbstractResponseMessage {

    private String lobbyName;

    private final ArrayList<UserDTO> users = new ArrayList<>();

    /**
     * Default Constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-08-13
     */
    public AllUsersInLobbyResponse() {
        // needed for serialization
    }

    /**
     * Constructor
     *
     * <p>This constructor generates a new List of the logged in users from the given Collection.
     * The significant difference between the two being that the new List contains copies of the
     * User objects. These copies have their password variable set to an empty String.
     *
     * @param users Collection of all users currently logged in
     * @since 2019-08-13
     */
    public AllUsersInLobbyResponse(String lobbyName, Collection<User> users) {
        this.lobbyName = lobbyName;
        for (User user : users) {
            this.users.add(UserDTO.createWithoutPassword(user));
        }
    }

    /**
     * Getter for the list of users currently logged in
     *
     * @return list of users currently logged in
     * @since 2019-08-13
     */
    public List<UserDTO> getUsers() {
        return users;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AllUsersInLobbyResponse that = (AllUsersInLobbyResponse) o;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), users);
    }
}
