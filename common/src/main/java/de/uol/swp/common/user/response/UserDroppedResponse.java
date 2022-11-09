package de.uol.swp.common.user.response;
import de.uol.swp.common.message.AbstractResponseMessage;
import de.uol.swp.common.user.User;

/**
 * A message to indicate a user is dropped
 * This message is used to automatically update every connected
 * client as soon as a user is dropped.
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2022-11-08
 */
public class UserDroppedResponse extends AbstractResponseMessage {

    private final String userDropped;

    public UserDroppedResponse(String user){
        this.userDropped = user;
    }

    public String getUsername() {
        return userDropped;
    }


}