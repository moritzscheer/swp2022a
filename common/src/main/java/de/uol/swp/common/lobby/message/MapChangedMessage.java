package de.uol.swp.common.lobby.message;

import de.uol.swp.common.game.Map;

public class MapChangedMessage extends AbstractLobbyMessage {

    private final Map newMap;

    public MapChangedMessage(Map m)
    {
        this.newMap = m;
    }

    public Map getMap()
    {
        return newMap;
    }

}
