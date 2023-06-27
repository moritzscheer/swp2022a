package de.uol.swp.common.user.message;

import de.uol.swp.common.message.AbstractMessage;
import de.uol.swp.common.user.User;

/**
 * A Message send from client to server, trying to go back to main-menu with username and password
 *
 * @see ReturnToMainMenuMessage
 * @author Waldemar Kempel, Maria Eduarda Costa Leite Andrade and Tommy Dang
 * @since 2022-12-02
 */
public class ReturnToMainMenuMessage extends AbstractMessage {

    private final User loggedInUser;

    public ReturnToMainMenuMessage(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
