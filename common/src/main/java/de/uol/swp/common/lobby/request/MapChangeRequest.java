package de.uol.swp.common.lobby.request;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.user.UserDTO;

public class MapChangeRequest extends AbstractLobbyRequest {

    private final Map newMap;

    /**
     * Constructor for MapChangeRequests
     * @param lobby The name of the lobby
     * @param user The user responsible for creating this message
     * @param newMap The new Map
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public MapChangeRequest(String lobby, UserDTO user, Map newMap)
    {
        super(lobby, user);
        this.newMap = newMap;
    }

    /**
     * Getter for the map
     *
     * @return The map contained in the request
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public Map getMap()
    {
        return newMap;
    }

}
