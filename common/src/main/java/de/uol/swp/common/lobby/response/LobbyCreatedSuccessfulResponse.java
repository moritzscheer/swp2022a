package de.uol.swp.common.lobby.response;

import de.uol.swp.common.user.UserDTO;

import java.util.Objects;

public class LobbyCreatedSuccessfulResponse extends AbstractLobbyResponse {

    private Integer lobbyID;
    private Boolean multiplayer;
    public LobbyCreatedSuccessfulResponse(String name, UserDTO user, Boolean multiplayer, Integer lobbyID) {
        super(name, user);
        this.multiplayer = multiplayer;
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the User variable
     *
     * @return User object of the user who successfully logged in
     * @since 2019-08-07
     */
    public UserDTO getUserDTO() {
        return getUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyCreatedSuccessfulResponse that = (LobbyCreatedSuccessfulResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    public Boolean isMultiplayer() {
        return multiplayer;
    }

    public Integer getLobbyID() {
        return this.lobbyID;
    }
}
