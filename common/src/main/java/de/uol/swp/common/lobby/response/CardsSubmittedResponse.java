package de.uol.swp.common.lobby.response;

public class CardsSubmittedResponse extends AbstractLobbyResponse {

    private Integer lobbyID;

    /**
     * Constructor
     *
     * @param lobbyID To identify the lobby with a unique key
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public CardsSubmittedResponse(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
