package de.uol.swp.common.user.response;

import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.user.User;

/**
 * A message containing the session (typically for an updated user)
 *
 * <p>This response is sent to the Client whose UpdateUserRequest was successful
 *
 * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
 * @see de.uol.swp.common.user.User
 * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
 * @since 2022-12-02
 */
public class UpdatedUserSuccessfulResponse extends AbstractResponseMessage {

    private final User updatedUser;

    public UpdatedUserSuccessfulResponse(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }
}
