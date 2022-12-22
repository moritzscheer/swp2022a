package de.uol.swp.client.tab.event;

import javafx.scene.Parent;

public class ShowNewTabEvent {

    private Integer lobbyID;
    private String lobbyName;
    private Boolean multiplayer;
    private Parent parent;


    public ShowNewTabEvent(Integer lobbyID, String lobbyName, Boolean multiplayer, Parent parent) {
        this.lobbyID = lobbyID;
        this.lobbyName = lobbyName;
        this.multiplayer = multiplayer;
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
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
