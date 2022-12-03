package de.uol.swp.common.user.request;

import de.uol.swp.common.message.AbstractRequestMessage;
import de.uol.swp.common.user.User;

/**
 * A request send from client to server, trying to log in the account options with
 * username and password
 *
 * @see de.uol.swp.common.user.request.ShowAccountOptionsRequest
 * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
 * @since  2022-12-02
 */

public class ShowAccountOptionsRequest extends AbstractRequestMessage {

    private User loggedInUser;

    public ShowAccountOptionsRequest(User loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
