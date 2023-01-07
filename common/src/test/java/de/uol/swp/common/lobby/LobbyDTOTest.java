package de.uol.swp.common.lobby;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for the UserDTO
 *
 * @author Marco Grawunder
 * @since 2019-10-08
 */
class LobbyDTOTest {

    private static final User defaultUser = new UserDTO("marco", "marco", "marco@grawunder.de");
    private static final User notInLobbyUser = new UserDTO("no", "marco", "no@grawunder.de");

    private static final int NO_USERS = 7;
    private static final List<UserDTO> users;

    static {
        users = new ArrayList<>();
        for (int i = 0; i < NO_USERS; i++) {
            users.add(new UserDTO("marco" + i, "marco" + i, "marco" + i + "@grawunder.de"));
        }
        Collections.sort(users);
    }

    // -----------------------------------------------------
    // Create Lobby Tests
    // -----------------------------------------------------

    /**
     * This test check whether a lobby is created correctly
     *
     * If the variables are not set correctly the test fails
     *
     * @author Moritz Scheer
     * @since 2019-10-08
     */
    @Test
    void createLobbyTest() {
        Lobby lobbyMP = new LobbyDTO(1, "test", defaultUser, "1234", true);

        assertEquals("test", lobbyMP.getName());
        assertEquals(1, lobbyMP.getUsers().size());
        assertEquals(defaultUser, lobbyMP.getUsers().iterator().next());
        assertEquals("1234", lobbyMP.getPassword());
        assertEquals(true, lobbyMP.isMultiplayer());

        Lobby lobbySP = new LobbyDTO(2, null, defaultUser, null, false);

        assertEquals(null, lobbySP.getName());
        assertEquals(1, lobbySP.getUsers().size());
        assertEquals(defaultUser, lobbySP.getUsers().iterator().next());
        assertEquals(null, lobbySP.getPassword());
        assertEquals(false, lobbySP.isMultiplayer());
    }

    // -----------------------------------------------------
    // Join User Lobby Tests
    // -----------------------------------------------------

    /**
     * This test check whether a user can join a lobby
     *
     * The test fails if the size of the user list of the lobby does not get bigger
     * or a user who joined is not in the list.
     *
     * Else the test fails, if a user can join a singleplayer lobby or puts in an incorrect password.+
     *
     * @author Moritz Scheer
     * @since 2019-10-08
     */
    @Test
    void joinUserLobbyTest() {
        Lobby lobbyMP = new LobbyDTO(1, "test", defaultUser, "1234", true);

        lobbyMP.joinUser(users.get(0), "1234");
        assertEquals(2,lobbyMP.getUsers().size());
        assertTrue(lobbyMP.getUsers().contains(users.get(0)));

        lobbyMP.joinUser(users.get(1),"1234");
        assertEquals(3,lobbyMP.getUsers().size());
        assertTrue(lobbyMP.getUsers().contains(users.get(1)));

        //password is incorrect
        assertThrows(IllegalArgumentException.class, () -> lobbyMP.joinUser(users.get(2), "4321"));

        lobbyMP.joinUser(users.get(2),"1234");
        lobbyMP.joinUser(users.get(3),"1234");
        lobbyMP.joinUser(users.get(4),"1234");
        lobbyMP.joinUser(users.get(5),"1234");
        lobbyMP.joinUser(users.get(6),"1234");

        //lobby is full
        assertThrows(IllegalArgumentException.class, () -> lobbyMP.joinUser(notInLobbyUser, "4321"));



        Lobby lobbySP = new LobbyDTO(2, null, defaultUser, null, false);

        //cannot join singleplayer lobby
        assertThrows(IllegalArgumentException.class, () -> lobbySP.joinUser(users.get(2), "4321"));
    }

    /**
     * This test check whether a user can leave a lobby
     *
     * The test fails if the size of the user list of the lobby does not get smaller
     * or the user who left is still in the list.
     *
     * @since 2019-10-08
     */
    @Test
    void leaveUserLobbyTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true);
        for(User user: users) {
            lobby.joinUser(user, "1234");
        }

        assertEquals(lobby.getUsers().size(), users.size() + 1);
        lobby.leaveUser(users.get(5));

        assertEquals(lobby.getUsers().size(), users.size() + 1 - 1);
        assertFalse(lobby.getUsers().contains(users.get(5)));
    }

    /**
     * Test to check if the owner can leave the Lobby correctly
     *
     * This test fails if the owner field is not updated if the owner leaves the
     * lobby or if he still is in the user list of the lobby.
     *
     * @since 2019-10-08
     */
    @Test
    void removeOwnerFromLobbyTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true);
        for(User user: users) {
            lobby.joinUser(user, "1234");
        }

        lobby.leaveUser(defaultUser);

        assertNotEquals(defaultUser, lobby.getOwner() );
        assertTrue(users.contains(lobby.getOwner()));

    }

    /**
     * This checks if the owner of a lobby can be updated and if he has have joined
     * the lobby
     *
     * This test fails if the owner cannot be updated or does not have to be joined
     *
     * @since 2019-10-08
     */
    @Test
    void updateOwnerTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true);
        for(User user: users) {
            lobby.joinUser(user, "1234");
        }

        lobby.updateOwner(users.get(6));
        assertEquals(lobby.getOwner(), users.get(6));

        assertThrows(IllegalArgumentException.class, () -> lobby.updateOwner(notInLobbyUser));
    }

    /**
     * This test check whether a lobby can be empty
     *
     * If the leaveUser function does not throw an Exception the test fails
     *
     * @since 2019-10-08
     */
    @Test
    void assureNonEmptyLobbyTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true);

        assertThrows(IllegalArgumentException.class, () -> lobby.leaveUser(defaultUser));
    }

    /**
     * Tests if a map can be updated
     */
    @Test
    void updateMapTest()
    {
        Map m = new Map(0);
        LobbyDTO lobby = new LobbyDTO(1, "test", defaultUser, "1234", true);

        Map before = lobby.getMap();
        lobby.setMap(m);
        Map after = lobby.getMap();

        assertNotEquals(before, after);
        assertEquals(m, after);
    }
}
