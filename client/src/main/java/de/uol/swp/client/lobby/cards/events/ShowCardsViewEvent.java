package de.uol.swp.client.lobby.cards.events;

public class ShowCardsViewEvent {

    private Integer lobbyID;

    public ShowCardsViewEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
