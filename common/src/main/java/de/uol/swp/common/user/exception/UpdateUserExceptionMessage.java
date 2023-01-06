package de.uol.swp.common.user.exception;

import de.uol.swp.common.message.AbstractResponseMessage;

import java.util.Objects;

/**
 * This exception is thrown if something went wrong during the user update process. e.g.: The
 * username does not exist
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2022-12-09
 */
public class UpdateUserExceptionMessage extends AbstractResponseMessage {

    private final String message;

    /**
     * Constructor
     *
     * @param message String containing the reason why the update failed
     * @since 2022-12-09
     */
    public UpdateUserExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UpdateUserExceptionMessage " + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserExceptionMessage that = (UpdateUserExceptionMessage) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
