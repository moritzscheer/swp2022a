package de.uol.swp.common.lobby.request;

public class SubmitCardsRequest extends AbstractLobbyRequest {

    private Integer lobbyID;

    public SubmitCardsRequest(Integer lobbyID){
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
