package de.uol.swp.common.lobby.response;

import de.uol.swp.common.user.UserDTO;

import java.util.Objects;

public class LobbyDroppedResponse extends AbstractLobbyResponse{

    public LobbyDroppedResponse(String name, UserDTO user) {
        super(name, user);
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
        LobbyCreatedResponse that = (LobbyCreatedResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
