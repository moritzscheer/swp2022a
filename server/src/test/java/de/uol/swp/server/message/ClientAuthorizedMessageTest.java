package de.uol.swp.server.message;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ClientAuthorizedMessageTest {

    UserDTO userDTO = new UserDTO("testUser", "pw", "mail");
    UserDTO userDTO2 = new UserDTO("testUser", "pw", "mail");

    @Test
    public void testGetUser() {
        User user = userDTO;
        ClientAuthorizedMessage message = new ClientAuthorizedMessage(user);

        User actualUser = message.getUser();

        Assertions.assertEquals(user, actualUser);
    }

    @Test
    public void testEquals() {
        User user1 = userDTO;
        User user2 = userDTO2;

        ClientAuthorizedMessage message1 = new ClientAuthorizedMessage(user1);
        ClientAuthorizedMessage message2 = new ClientAuthorizedMessage(user1);
        ClientAuthorizedMessage message3 = new ClientAuthorizedMessage(user2);

        // Same instance
        Assertions.assertEquals(message1, message1);

        // Same attributes but different instance
        Assertions.assertEquals(message1, message2);

        // Same attributes but different sequence
        Assertions.assertEquals(message2, message1);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());

        Assertions.assertEquals(message1, message3);
        Assertions.assertEquals(message3, message1);
    }
}
