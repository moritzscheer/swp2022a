package de.uol.swp.common.game.message;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RobotIsFinallyDeadTest {

    UserDTO userDied = new UserDTO("Test", "pw", "mail");
    UserDTO userDied2 = new UserDTO("Test2", "pw", "mail");
    UserDTO userDied3 = new UserDTO("Test3", "pw", "mail");
    UserDTO userDied4 = new UserDTO("Test4", "pw", "mail");

    /**
     * Tests the getter of the LobbyID
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an Exception
     * @result The method should return true if the user is in the list and the lobbyID is the same
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        RobotIsFinallyDead message = new RobotIsFinallyDead(lobbyID, userDied);

        int result = message.getLobbyID();

        Assertions.assertEquals(lobbyID, result);
    }

    /**
     * Tests the getter of the died User
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an Exception
     * @result The method should return true if the user is in the list and the lobbyID is the same
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @since 2023-06-14
     */
    @Test
    public void testGetUserDied() {
        int lobbyID = 123;
        RobotIsFinallyDead message = new RobotIsFinallyDead(lobbyID, userDied);

        UserDTO result = message.getUserDied();

        Assertions.assertEquals(userDied, result);
    }

    /**
     * Tests the getter of the died UserList
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an Exception
     * @result The method should return true if the user is in the list and the lobbyID is the same
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @since 2023-06-14
     */
    @Test
    public void testGetUserListDied() {
        int lobbyID = 123;
        List<RobotIsFinallyDead> listMessage = new ArrayList<>();
        RobotIsFinallyDead message1 = new RobotIsFinallyDead(lobbyID, userDied);
        RobotIsFinallyDead message2 = new RobotIsFinallyDead(lobbyID, userDied2);
        RobotIsFinallyDead message3 = new RobotIsFinallyDead(lobbyID, userDied3);
        RobotIsFinallyDead message4 = new RobotIsFinallyDead(lobbyID, userDied4);
        listMessage.add(message1);
        listMessage.add(message2);
        listMessage.add(message3);
        listMessage.add(message4);

        List<UserDTO> result = new ArrayList<>();
        for (RobotIsFinallyDead robotIsFinallyDead : listMessage) {
            result.add(robotIsFinallyDead.getUserDied());
        }

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(listMessage.get(i).getUserDied(), result.get(i));
        }
    }

    /**
     * Tests the getter of the died UserList
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an Exception
     * @result The method should return true if the user is not in the list
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @since 2023-06-14
     */
    @Test
    public void testGetUserListNotDied() {
        int lobbyID = 123;
        UserDTO userAlive = new UserDTO("Alive", "pw", "mail");
        List<RobotIsFinallyDead> listMessage = new ArrayList<>();
        RobotIsFinallyDead message2 = new RobotIsFinallyDead(lobbyID, userDied2);
        RobotIsFinallyDead message3 = new RobotIsFinallyDead(lobbyID, userDied3);
        RobotIsFinallyDead message4 = new RobotIsFinallyDead(lobbyID, userDied4);
        listMessage.add(message2);
        listMessage.add(message3);
        listMessage.add(message4);

        List<UserDTO> result = new ArrayList<>();
        for (RobotIsFinallyDead robotIsFinallyDead : listMessage) {
            result.add(robotIsFinallyDead.getUserDied());
        }

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getUsername().equals(userAlive.getUsername())) {
                Assertions.fail();
            } else {
                Assertions.assertEquals(listMessage.get(i).getUserDied(), result.get(i));
            }
        }
    }

    /**
     * Tests the getter of the lobbyID with a negative value
     *
     * @author WKempel
     * @result The constructor should throw an IllegalArgumentException
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }

    /**
     * Tests the equals method and the hashCode method
     *
     * @author WKempel
     * @result The methods should work without throwing an Exception
     * @result The method should return true if the messages are the same
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @since 2023-06-14
     */
    @Test
    public void testEqualsAndHashCode() {
        int lobbyID = 123;

        RobotIsFinallyDead message1 = new RobotIsFinallyDead(lobbyID, userDied);
        RobotIsFinallyDead message2 = new RobotIsFinallyDead(lobbyID, userDied);

        Assertions.assertEquals(message1, message2);
        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
