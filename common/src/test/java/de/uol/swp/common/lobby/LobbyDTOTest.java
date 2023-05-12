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
    private static final List<UserDTO> users;

    static {
        users = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            users.add(new UserDTO("marco" + i, "marco" + i, "marco" + i + "@grawunder.de"));
        }
        Collections.sort(users);
    }

    // -----------------------------------------------------
    // Create Lobby Tests
    // -----------------------------------------------------

    /**
     * This test check whether the createWithoutPassword method creates the lobby correctly
     *
     * The test fails, if the lobby password is still set
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void createWithoutPasswordTest() {
        LobbyDTO lobby = new LobbyDTO(1, "lobby", defaultUser, "1234", true, null);
        lobby = lobby.createWithoutPassword(lobby);

        assertEquals(1, lobby.getLobbyID());
        assertEquals("lobby", lobby.getName());
        assertEquals(defaultUser, lobby.getOwner());
        assertEquals("****", lobby.getPassword());
        assertEquals(true, lobby.isMultiplayer());

        LobbyDTO lobby2 = new LobbyDTO(1, "lobby", defaultUser, "", true, null);
        lobby2 = lobby2.createWithoutPassword(lobby2);

        assertEquals(1, lobby2.getLobbyID());
        assertEquals("lobby", lobby2.getName());
        assertEquals(defaultUser, lobby2.getOwner());
        assertEquals("", lobby2.getPassword());
        assertEquals(true, lobby2.isMultiplayer());
    }

    /**
     * This test check whether the createWithoutUserPassword method creates the lobby correctly
     *
     * The test fails, if the passwords of the users are still set
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void createWithoutUserPassword() {
        LobbyDTO lobby = new LobbyDTO(1, "lobby", defaultUser, "1234", true, null);
        for(User user: users) { lobby.joinUser(user, "1234"); }
        lobby = lobby.createWithoutUserPassword(lobby);

        assertEquals(1, lobby.getLobbyID());
        assertEquals("lobby", lobby.getName());
        assertEquals(defaultUser, lobby.getOwner());
        assertEquals("1234", lobby.getPassword());
        assertEquals(true, lobby.isMultiplayer());

        for(User user: lobby.getUsers()) {
            assertEquals("", user.getPassword());
        }
    }

    // -----------------------------------------------------
    // Join Lobby Tests
    // -----------------------------------------------------

    /**
     * This test check whether an IllegalArgumentException is thrown,
     * if the user wants to join a singleplayer lobby
     *
     * The test fails, if no exception is thrown
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void joinUserSingleplayerLobbyTest() {
        Lobby lobby = new LobbyDTO(2, null, defaultUser, null, false, null);

        assertThrows(IllegalArgumentException.class, () -> lobby.joinUser(users.get(2), "4321"));
    }

    /**
     * This test check whether a user can join a lobby
     *
     * The test fails, if the size of the user list of the lobby does not get bigger
     * or a user who joined is not in the list.
     *
     * Else the test fails, if a user can join a singleplayer lobby or puts in an incorrect password.
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void joinUserMultiplayerLobbyTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        lobby.joinUser(users.get(0), "1234");
        assertEquals(2,lobby.getUsers().size());
        assertTrue(lobby.getUsers().contains(users.get(0)));

        lobby.joinUser(users.get(1),"1234");
        assertEquals(3,lobby.getUsers().size());
        assertTrue(lobby.getUsers().contains(users.get(1)));
    }

    /**
     * This test check whether an IllegalArgumentException is thrown,
     * if the user wants to join the lobby, with an incorrect password
     *
     * The test fails, if no exception is thrown
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void joinUserPasswordIncorrectTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertThrows(IllegalArgumentException.class, () -> lobby.joinUser(users.get(0), "4321"));
    }

    /**
     * This test check whether a user can join a lobby when it is full
     *
     * The test fails, if the user can join the lobby, when the lobby slot of 8 is reached.
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void joinUserLobbyFullTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);
        for(User user: users) { lobby.joinUser(user, "1234"); }

        assertThrows(IllegalArgumentException.class, () -> lobby.joinUser(notInLobbyUser, "4321"));

    }

    // -----------------------------------------------------
    // leave Lobby Tests
    // -----------------------------------------------------

    /**
     * This test check whether a user can leave a lobby
     *
     * The test fails, if the size of the user list of the lobby does not get smaller
     * or the user who left is still in the list.
     *
     * @since 2019-10-08
     */
    @Test
    void leaveUserTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);
        for(User user: users) { lobby.joinUser(user, "1234"); }

        assertEquals(lobby.getUsers().size(), users.size() + 1);
        lobby.leaveUser(users.get(5));

        assertEquals(lobby.getUsers().size(), users.size() + 1 - 1);
        assertFalse(lobby.getUsers().contains(users.get(5)));
    }

    /**
     * This test check whether an IllegalArgumentException is thrown,
     * if the user that wants to leave the lobby and is the last user in the lobby
     *
     * The test fails, if no exception is thrown
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void leaveLastUserTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertThrows(IllegalArgumentException.class, () -> lobby.leaveUser(defaultUser));
    }

    /**
     * Test to check if the owner can leave the Lobby correctly
     *
     * This test fails, if the owner field is not updated if the owner leaves the
     * lobby or if he still is in the user list of the lobby.
     *
     * @author Moritz Scheer
     * @since 2019-10-08
     */
    @Test
    void removeOwnerFromLobbyTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);
        for(User user: users) {
            lobby.joinUser(user, "1234");
        }

        lobby.leaveUser(defaultUser);

        assertNotEquals(defaultUser, lobby.getOwner() );
        assertTrue(users.contains(lobby.getOwner()));
        assertEquals(lobby.getUsers().iterator().next(), lobby.getOwner());

    }

    // -----------------------------------------------------
    // other Tests
    // -----------------------------------------------------

    /**
     * This checks if the owner of a lobby can be updated and if he has have joined
     * the lobby
     *
     * This test fails, if the owner cannot be updated or does not have to be joined
     *
     * @author Moritz Scheer
     * @since 2019-10-08
     */
    @Test
    void updateOwnerTest() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);
        for(User user: users) {
            lobby.joinUser(user, "1234");
        }

        lobby.updateOwner(users.get(6));
        assertEquals(lobby.getOwner(), users.get(6));

        assertThrows(IllegalArgumentException.class, () -> lobby.updateOwner(notInLobbyUser));
    }

    /**
     * This test gets the owner of the lobby
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getOwner() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertEquals(defaultUser, lobby.getOwner());
    }

    /**
     * This test gets the users of the lobby
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getUsers() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertTrue(lobby.getUsers().contains(defaultUser));
        assertEquals(1, lobby.getUsers().size());
    }

    /**
     * Tests if a map can be updated
     */
    @Test
    void updateMapTest()
    {
        Map m = new Map(0);
        LobbyDTO lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        Map before = lobby.getMap();
        lobby.setMap(m);
        Map after = lobby.getMap();

        assertNotEquals(before, after);
        assertEquals(m, after);
    }

    /**
     * This test gets the password of the lobby
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getPassword() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertEquals("1234", lobby.getPassword());
    }

    /**
     * This test gets the game mode status of the lobby
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void isMultiplayer() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertEquals(true, lobby.isMultiplayer());
    }

    /**
     * This test gets the lobbyID of the lobby
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getLobbyID() {
        Lobby lobby = new LobbyDTO(1, "test", defaultUser, "1234", true, null);

        assertEquals(1, lobby.getLobbyID());
    }
}
