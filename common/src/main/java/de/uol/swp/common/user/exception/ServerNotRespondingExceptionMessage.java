package de.uol.swp.common.user.exception;

import de.uol.swp.common.message.AbstractResponseMessage;

import java.util.Objects;

public class ServerNotRespondingExceptionMessage extends AbstractResponseMessage {

    private final String message;

    /**
     * Constructor
     *
     * @param message String containing a message about why the server stopped responding
     * @since 2023-06-18
     */
    public ServerNotRespondingExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerNotRespondingMessage " + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerNotRespondingExceptionMessage that = (ServerNotRespondingExceptionMessage) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
