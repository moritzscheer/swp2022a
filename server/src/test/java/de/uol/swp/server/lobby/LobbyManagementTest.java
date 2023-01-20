package de.uol.swp.server.lobby;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class LobbyManagementTest {

    final LobbyManagement lobbyManagement = new LobbyManagement();

    private static final UserDTO defaultUser = new UserDTO("marco", "marco", "marco@grawunder.de");
    private static final UserDTO notInLobbyUser = new UserDTO("no", "marco", "no@grawunder.de");
    private static final List<UserDTO> users;

    static {
        users = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            users.add(new UserDTO("marco" + i, "marco" + i, "marco" + i + "@grawunder.de"));
        }
        Collections.sort(users);
    }

    private void addDefaultLobbies() {
        lobbyManagement.createLobby("lobby1", users.get(0), "1234", true);
        lobbyManagement.createLobby("lobby2", users.get(1), "4321", true);
        lobbyManagement.createLobby(null, users.get(0), null, false);
        lobbyManagement.createLobby(null, users.get(1), null, false);
    }

    // ------------------------------------------
    // createLobby tests
    // ------------------------------------------

    /**
     * This test check whether a multiplayer lobby is created correctly
     *
     * <p>If the variables are not set correctly the test fails
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void createMultiplayerLobbyTest() {
        lobbyManagement.createLobby("lobby1", defaultUser, "1234", true);

        assertNotNull(lobbyManagement.getLobbies());
        assertEquals(1, lobbyManagement.getLobbies().size());
        assertEquals("lobby1", lobbyManagement.getLobby(1).get().getName());
        assertEquals(defaultUser, lobbyManagement.getLobby(1).get().getOwner());
        assertEquals("1234", lobbyManagement.getLobby(1).get().getPassword());
        assertEquals(true, lobbyManagement.getLobby(1).get().isMultiplayer());
    }

    /**
     * This test check whether an IllegalArgumentException is thrown, if the user wants to create a
     * singleplayer lobby with a name that is already given
     *
     * <p>The test fails, if no exception is thrown
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void createMultiplayerLobbyWithSameNameTest() {
        addDefaultLobbies();
        assertThrows(
                IllegalArgumentException.class,
                () -> lobbyManagement.createLobby("lobby1", users.get(0), "1234", true));
    }

    /**
     * This test check whether a singleplayer lobby is created correctly
     *
     * <p>If the variables are not set correctly the test fails
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void createSingleplayerLobbyTest() {
        lobbyManagement.createLobby(null, defaultUser, null, false);

        assertNotNull(lobbyManagement.getLobbies());
        assertEquals(1, lobbyManagement.getLobbies().size());
        assertNull(lobbyManagement.getLobby(1).get().getName());
        assertEquals(defaultUser, lobbyManagement.getLobby(1).get().getOwner());
        assertNull(lobbyManagement.getLobby(1).get().getPassword());
        assertEquals(false, lobbyManagement.getLobby(1).get().isMultiplayer());
    }

    // ------------------------------------------
    // DropLobby test
    // ------------------------------------------

    /**
     * This test check whether a singleplayer lobby is created correctly
     *
     * <p>If the variables are not set correctly the test fails
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void dropLobbyTest() {
        lobbyManagement.createLobby("lobby1", defaultUser, "1234", true);

        assertFalse(lobbyManagement.getLobbies().isEmpty());

        lobbyManagement.dropLobby(1);

        assertTrue(lobbyManagement.getLobbies().isEmpty());
    }

    // ------------------------------------------
    // getLobby test
    // ------------------------------------------

    /**
     * This test check whether the getLobby method returns the correct lobby
     *
     * <p>If the variables are not set correctly the test fails
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getLobbyTest() {
        addDefaultLobbies();
        Optional<LobbyDTO> lobby1 = lobbyManagement.getLobby(1);
        Optional<LobbyDTO> lobby2 = lobbyManagement.getLobby(2);
        Optional<LobbyDTO> lobby5 = lobbyManagement.getLobby(5);

        assertNotEquals(Optional.empty(), lobby1);
        assertNotEquals(Optional.empty(), lobby2);
        assertEquals(Optional.empty(), lobby5);
    }

    // ------------------------------------------
    // getLobbies test
    // ------------------------------------------

    /**
     * This test check whether the getLobbies method returns all lobbies
     *
     * <p>If the variables are not set correctly the test fails
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getLobbiesTest() {
        addDefaultLobbies();

        assertEquals(4, lobbyManagement.getLobbies().size());

        assertEquals("lobby1", lobbyManagement.getLobbies().get(0).getName());
        assertEquals(users.get(0), lobbyManagement.getLobbies().get(0).getOwner());
        assertEquals("1234", lobbyManagement.getLobbies().get(0).getPassword());
        assertEquals(true, lobbyManagement.getLobbies().get(0).isMultiplayer());

        assertEquals("lobby2", lobbyManagement.getLobbies().get(1).getName());
        assertEquals(users.get(1), lobbyManagement.getLobbies().get(1).getOwner());
        assertEquals("4321", lobbyManagement.getLobbies().get(1).getPassword());
        assertEquals(true, lobbyManagement.getLobbies().get(1).isMultiplayer());

        assertNull(lobbyManagement.getLobbies().get(2).getName());
        assertEquals(users.get(0), lobbyManagement.getLobbies().get(2).getOwner());
        assertNull(lobbyManagement.getLobbies().get(2).getPassword());
        assertEquals(false, lobbyManagement.getLobbies().get(2).isMultiplayer());

        assertNull(lobbyManagement.getLobbies().get(3).getName());
        assertEquals(users.get(1), lobbyManagement.getLobbies().get(3).getOwner());
        assertNull(lobbyManagement.getLobbies().get(3).getPassword());
        assertEquals(false, lobbyManagement.getLobbies().get(3).isMultiplayer());
    }

    // ------------------------------------------
    // getMultiplayerLobbies test
    // ------------------------------------------

    /**
     * This test check whether the getMultiplayer method returns all multiplayer lobbies
     *
     * <p>If singleplayer lobbies are put in the test fails.
     *
     * @author Moritz Scheer
     * @since 2022-12-20
     */
    @Test
    void getMultiplayerLobbies() {
        addDefaultLobbies();

        assertEquals(2, lobbyManagement.getMultiplayerLobbies().size());

        assertEquals(users.get(0), lobbyManagement.getLobbies().get(0).getOwner());
        assertEquals(users.get(1), lobbyManagement.getLobbies().get(1).getOwner());
    }
}
