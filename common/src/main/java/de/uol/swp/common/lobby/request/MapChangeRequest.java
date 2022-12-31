package de.uol.swp.common.lobby.request;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.user.UserDTO;

public class MapChangeRequest extends AbstractLobbyRequest {

    private final Map newMap;

    public MapChangeRequest(String lobby, UserDTO user, Map newMap)
    {
        super(lobby, user);
        this.newMap = newMap;
    }

    public Map getMap()
    {
        return newMap;
    }

}
