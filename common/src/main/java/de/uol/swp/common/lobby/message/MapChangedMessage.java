package de.uol.swp.common.lobby.message;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.user.UserDTO;

public class MapChangedMessage extends AbstractLobbyMessage {

    private final Map newMap;
    private final int lobbyID;

    /**
     * The constructor for MapChangedMessages. These messages are to notify clients that the map of
     * a lobby has been changed
     *
     * @param lobbyID The ID of the lobby
     * @param user The user responsible for the creation of this message
     * @param m The map selected by the owner of the lobby
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public MapChangedMessage(int lobbyID, UserDTO user, Map m) {
        this.user = user;
        this.lobbyID = lobbyID;
        this.newMap = m;
    }

    /**
     * Getter for the Map object contained within the message
     *
     * @return The Map contained within the message
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public Map getMap() {
        return newMap;
    }

    public int getLobbyID() {
        return this.lobbyID;
    }
}
