package de.uol.swp.common.lobby.response;

import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Response message for the RetrieveAllOnlineLobbiesRequest
 *
 * This message gets sent to the client that sent an RetrieveAllOnlineLobbiesRequest.
 * It contains a List with Lobby objects of every currently open lobbies on the
 * server.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.common.message.AbstractResponseMessage
 * @see de.uol.swp.common.user.request.RetrieveAllOnlineUsersRequest
 * @see de.uol.swp.common.user.User
 * @since 2022-11-29
 */
public class AllOnlineLobbiesResponse extends AbstractResponseMessage {

    private final ArrayList<LobbyDTO> lobbies = new ArrayList<>();
    private final ArrayList<LobbyDTO> lobbiesWithoutPassword = new ArrayList<>();

    /**
     * Default Constructor
     *
     * @implNote this constructor is needed for serialization
     * @author Moritz Scheer
     * @since 2022-11-29
     */
    public AllOnlineLobbiesResponse(){
        // needed for serialization
    }

    /**
     * Constructor
     *
     * This constructor generates a new List of the logged-in users from the given
     * Collection. The significant difference between the two being that the new
     * List contains copies of the User objects. These copies have their password
     * variable set to an empty String.
     *
     * @param lobbyCollection Collection of all users currently logged in
     * @author Moritz Scheer
     * @since 2022-11-29
     */
    public AllOnlineLobbiesResponse(Collection<LobbyDTO> lobbyCollection) {
        for (LobbyDTO lobby : lobbyCollection) {
            if(lobby.getPassword().equals("")) {
                lobbiesWithoutPassword.add(lobby);
            }
            this.lobbies.add(lobby.createWithoutPassword(lobby));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AllOnlineLobbiesResponse that = (AllOnlineLobbiesResponse) o;
        return Objects.equals(lobbies, that.lobbies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lobbies);
    }

    /**
     * Getter for the list of lobbies
     *
     * @return list of lobbies
     * @author Moritz Scheer
     * @since 2022-11-29
     */
    public List<LobbyDTO> getLobbies() {
        return lobbies;
    }

    /**
     * Getter for the list of lobbies with no password
     *
     * @return list of lobbies with no password
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    public List<LobbyDTO> getLobbiesWithoutPassword() {
        return lobbiesWithoutPassword;
    }
}
