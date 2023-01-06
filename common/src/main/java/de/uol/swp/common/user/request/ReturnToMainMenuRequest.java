package de.uol.swp.common.user.request;

import de.uol.swp.common.message.AbstractRequestMessage;
import de.uol.swp.common.user.User;

/**
 * A request send from client to server, trying to go back to main-menu with username and password
 *
 * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
 * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
 * @since 2022-12-02
 */
public class ReturnToMainMenuRequest extends AbstractRequestMessage {

    private User loggedInUser;

    public ReturnToMainMenuRequest(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
