package de.uol.swp.common.lobby.exception;

import de.uol.swp.common.lobby.response.AbstractLobbyResponse;
import de.uol.swp.common.user.UserDTO;

import java.util.Objects;

public class LobbyLeftExceptionResponse extends AbstractLobbyResponse {

    private final String message;
    private final Integer lobbyID;

    /**
     * Constructor
     *
     * @param message String containing the reason why the registration failed
     * @since 2022-11-24
     */
    public LobbyLeftExceptionResponse(
            Integer lobbyID, String lobbyName, UserDTO user, String message) {
        super(lobbyName, user);
        this.message = message;
        this.lobbyID = lobbyID;
    }

    @Override
    public String toString() {
        return "LobbyExceptionResponse " + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyLeftExceptionResponse that = (LobbyLeftExceptionResponse) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
