package de.uol.swp.client.lobby;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import de.uol.swp.common.lobby.request.CreateLobbyRequest;
import de.uol.swp.common.lobby.request.LobbyJoinUserRequest;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.request.RetrieveAllUsersInLobbyRequest;

/**
 * Classes that manages lobbies
 *
 * @author Marco Grawunder
 * @since 2019-11-20
 *
 */
@SuppressWarnings("UnstableApiUsage")
public class LobbyService {

    private final EventBus eventBus;

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @see de.uol.swp.client.di.ClientModule
     * @since 2019-11-20
     */
    @Inject
    public LobbyService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    /**
     * Posts a request to create a lobby on the EventBus
     *
     * @param name Name chosen for the new lobby
     * @param user User who wants to create the new lobby
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @since 2019-11-20
     */
    public void createNewLobby(String name, UserDTO user, Boolean isMultiplayer, String password) {
        CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest(name, user, isMultiplayer, password);
        eventBus.post(createLobbyRequest);
    }

    /**
     * Posts a request to join a specified lobby on the EventBus
     *
     * @param name Name of the lobby the user wants to join
     * @param user User who wants to join the lobby
     * @see de.uol.swp.common.lobby.request.LobbyJoinUserRequest
     * @since 2019-11-20
     */
    public void joinLobby(String name, UserDTO user) {
        LobbyJoinUserRequest joinUserRequest = new LobbyJoinUserRequest(name, user);
        eventBus.post(joinUserRequest);
    }


    public void retrieveAllUsersInLobby(String lobbyName) {
        RetrieveAllUsersInLobbyRequest cmd = new RetrieveAllUsersInLobbyRequest(lobbyName);
        eventBus.post(cmd);
    }
}
