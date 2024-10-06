package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player;
    private UserDTO userDTO = new UserDTO("User1", "password", "someEmail");
    private Position startPosition = new Position(0, 0);
    private int robotID = 1;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-23
     */
    @BeforeEach
    public void setUp() {
        player = new Player(userDTO, startPosition, robotID);
    }

    /**
     * Tests the setUser method
     *
     * @author WKempel
     * @since 2023-06-23
     */
    @Test
    public void testSetUser() {
        UserDTO newUser = new UserDTO("newUsername", "newPassword", "newEmail");
        player.setUser(newUser);
        Assertions.assertEquals(newUser, player.getUser());
    }
}
