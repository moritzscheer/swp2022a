package de.uol.swp.common.lobby.request;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.user.UserDTO;

public class MapChangeRequest extends AbstractLobbyRequest {

    private final Map newMap;
    private final int lobbyID;

    /**
     * Constructor for MapChangeRequests
     * @param lobbyID The ID of the lobby
     * @param user The user responsible for creating this message
     * @param newMap The new Map
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public MapChangeRequest(int lobbyID, UserDTO user, Map newMap)
    {
        this.user = user;
        this.lobbyID = lobbyID;
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

    public int getID()
    {
        return lobbyID;
    }

}
