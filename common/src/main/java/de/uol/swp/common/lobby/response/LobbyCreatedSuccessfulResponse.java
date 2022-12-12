package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.Lobby;
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
public class LobbyCreatedSuccessfulResponse extends AbstractLobbyResponse {

    private final LobbyDTO lobby;

    /**
     * Constructor
     *
     * @param lobby The name the lobby should have
     * @param user The user who created the lobby and therefore shall be the owner
     * @author Moritz Scheer
     * @since 2022-12-01
     */
    public LobbyCreatedSuccessfulResponse(Lobby lobby, UserDTO user) {
        super(lobby.getName(), user);
        this.lobby = (LobbyDTO) lobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyCreatedSuccessfulResponse that = (LobbyCreatedSuccessfulResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    /**
     * Getter for the lobby variable
     *
     * @return LobbyDTO object of the lobby
     * @author Moritz Scheer
     * @since 2022-12-01
     */
    public LobbyDTO getLobby() {
        return lobby;
    }

    public Boolean isMultiplayer() {
        return lobby.isMultiplayer();
    }

    public Integer getLobbyID() {
        return lobby.getLobbyID();
    }
}
