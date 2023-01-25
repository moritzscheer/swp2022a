package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * Response sent to the Client with all the lobby data in it
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Moritz Scheer
 * @since 2022-11-17
 */
public class LobbyCreatedSuccessfulResponse extends AbstractLobbyResponse {

    private final LobbyDTO lobby;

    /**
     * Constructor
     *
     * @param lobby The name the lobby should have
     * @param user The user who created the lobby and therefore shall be the owner
     * @author Moritz Scheer
     * @since 2022-12-01
     */
    public LobbyCreatedSuccessfulResponse(Lobby lobby, UserDTO user) {
        super(lobby.getName(), user);
        this.lobby = (LobbyDTO) lobby;
    }

    /**
     * Getter for the user variable of the parent class
     *
     * @return UserDTO containing the user's data
     * @author Ole Zimmermann
     * @since 2023-01-24
     */
    public UserDTO getUser(){
        return super.getUser();
    }

    /**
     * Getter for the lobby variable
     *
     * @return LobbyDTO object of the lobby
     * @author Moritz Scheer
     * @since 2022-12-01
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
