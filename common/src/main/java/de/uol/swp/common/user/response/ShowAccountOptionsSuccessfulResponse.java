package de.uol.swp.common.user.response;

import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.user.User;

/**
 * A message containing the session (typically for an user who went to the AccountOptions)
 *
 * This response is sent to the Client whose ShowAccountOptionsRequest was successful
 *
 * @see de.uol.swp.common.user.response.ShowAccountOptionsSuccessfulResponse
 * @see de.uol.swp.common.user.User
 * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
 * @since 2022-12-02
 */
public class ShowAccountOptionsSuccessfulResponse extends AbstractResponseMessage {

    private User loggedInUser;

    public ShowAccountOptionsSuccessfulResponse(User loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
