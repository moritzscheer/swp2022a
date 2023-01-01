package de.uol.swp.common.lobby.message;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.user.UserDTO;

public class MapChangedMessage extends AbstractLobbyMessage {

    private final Map newMap;

    /**
     * The constructor for MapChangedMessages.
     * These messages are to notify clients that the map of a lobby has been changed
     *
     * @param name The name of the lobby
     * @param user The user responsible for the creation of this message
     * @param m The map selected by the owner of the lobby
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public MapChangedMessage(String name, UserDTO user, Map m)
    {
        super(name, user);
        this.newMap = m;
    }

    /**
     * Getter for the Map object contained within the message
     * @return The Map contained within the message
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public Map getMap()
    {
        return newMap;
    }

}
