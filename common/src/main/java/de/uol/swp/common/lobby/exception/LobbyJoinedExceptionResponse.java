package de.uol.swp.common.lobby.exception;

import de.uol.swp.common.lobby.response.AbstractLobbyResponse;

import java.util.Objects;

public class LobbyJoinedExceptionResponse extends AbstractLobbyResponse {

    private final String message;

    /**
     * Constructor
     *
     * @param message String containing the reason why the registration failed
     * @since 2022-11-24
     */
    public LobbyJoinedExceptionResponse(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return "LobbyExceptionResponse "+message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyJoinedExceptionResponse that = (LobbyJoinedExceptionResponse) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}