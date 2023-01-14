package de.uol.swp.common.user.request;

import de.uol.swp.common.message.AbstractRequestMessage;
import de.uol.swp.common.user.User;

import java.util.Objects;

/**
 * A request send from client to server, trying to delete a user
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2022-11-08
 */
public class DropUserRequest extends AbstractRequestMessage {

    private final User toDrop;
    /**
     * Constructor
     *
     * @param user the User to drop
     * @since 2022-11-08
     */
    public DropUserRequest(User user) {
        this.toDrop = user;
    }

    public User getUser() {
        return toDrop;
    }

    public String getUsername() {
        return toDrop.getUsername();
    }
    ;

    @Override
    public boolean authorizationNeeded() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DropUserRequest that = (DropUserRequest) o;
        return Objects.equals(toDrop, that.toDrop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toDrop);
    }
}
