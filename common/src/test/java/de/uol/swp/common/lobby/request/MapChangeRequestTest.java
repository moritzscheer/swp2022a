package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MapChangeRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyID, user and map
     * @see de.uol.swp.common.lobby.request.MapChangeRequest
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {
        int lobbyID = 123;
        Map map = null;

        MapChangeRequest request =
                new MapChangeRequest(lobbyID, user, (de.uol.swp.common.game.Map) map);

        Assertions.assertEquals(lobbyID, request.getID());
        Assertions.assertEquals(user, request.getUser());
        Assertions.assertEquals(map, request.getMap());
    }

    /**
     * Tests the getMap method
     *
     * @author WKempel
     * @result The getter should return the correct map
     * @see de.uol.swp.common.lobby.request.MapChangeRequest
     * @since 2023-06-16
     */
    @Test
    public void testGetMap() {
        int lobbyID = 123;
        Map map = null;

        MapChangeRequest request =
                new MapChangeRequest(lobbyID, user, (de.uol.swp.common.game.Map) map);

        Map retrievedMap = (Map) request.getMap();

        Assertions.assertEquals(map, retrievedMap);
    }

    /**
     * Tests the getID method
     *
     * @author WKempel
     * @result The getter should return the correct lobbyID
     * @see de.uol.swp.common.lobby.request.MapChangeRequest
     * @since 2023-06-16
     */
    @Test
    public void testGetID() {
        int lobbyID = 123;
        Map map = null;

        MapChangeRequest request =
                new MapChangeRequest(lobbyID, user, (de.uol.swp.common.game.Map) map);

        int retrievedID = request.getID();

        Assertions.assertEquals(lobbyID, retrievedID);
    }
}
