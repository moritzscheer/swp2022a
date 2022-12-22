package de.uol.swp.client.lobby.event;

import javafx.scene.Parent;

/**
 * * Event used to show the Lobby window
 *
 * In order to show the Lobby window using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.SceneManager
 * @since 2022-15-11
 *
 */
public class ShowLobbyViewEvent {

    private Integer lobbyID;
    private String lobbyName;
    private Boolean multiplayer;

    public ShowLobbyViewEvent(Integer lobbyID, String lobbyName, Boolean multiplayer) {
        this.lobbyID = lobbyID;
        this.lobbyName = lobbyName;
        this.multiplayer = multiplayer;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public Boolean isMultiplayer() {
        return multiplayer;
    }
}
