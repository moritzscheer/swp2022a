package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import java.util.Objects;

public class LobbyJoinedSuccessfulResponse extends AbstractLobbyResponse {

    private final LobbyDTO lobby;

    public LobbyJoinedSuccessfulResponse(Lobby lobby, UserDTO user) {
        super(lobby.getName(), user);

        this.lobby = new LobbyDTO(lobby.getLobbyID(), lobby.getName(), lobby.getOwner().getWithoutPassword(), lobby.getPassword(), lobby.isMultiplayer());
        for (User users : lobby.getUsers()) {
            if(!user.equals(lobby.getOwner()))
                this.lobby.getUsers().add(UserDTO.createWithoutPassword(users));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyJoinedSuccessfulResponse that = (LobbyJoinedSuccessfulResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    public LobbyDTO getLobby() {
        return lobby;
    }
}
