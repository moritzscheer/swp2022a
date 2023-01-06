package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import java.util.Objects;

/**
 * Response sent to the Client with all the lobby data in it
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Moritz Scheer
 * @since 2022-11-17
 */
public class LobbyJoinedSuccessfulResponse extends AbstractLobbyResponse {

    private final LobbyDTO lobby;

    /**
     * Constructor
     *
     * @param lobby The name of the lobby that the player wants to join in
     * @param user The user who wants to join the lobby
     * @since 2022-12-03
     */
    public LobbyJoinedSuccessfulResponse(LobbyDTO lobby, UserDTO user) {
        super(lobby.getName(), user);
        this.lobby = lobby/*.createWithoutUserPassword(lobby)*/;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyJoinedSuccessfulResponse that = (LobbyJoinedSuccessfulResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    /**
     * Getter for LobbyDTO
     *
     * @return LobbyDTO containing the lobby
     * @author Moritz Scheer
     * @since 2022-12-03
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
