package de.uol.swp.client.lobby;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import de.uol.swp.common.lobby.request.CreateLobbyRequest;
import de.uol.swp.common.lobby.request.LobbyJoinUserRequest;
import de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest;
import de.uol.swp.common.lobby.request.LobbyLeaveUserRequest;
import de.uol.swp.common.user.UserDTO;

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
     * @author Moritz Scheer
     * @since 2022-11-23
     */
    public void createNewLobby(String name, UserDTO user, Boolean multiplayer, String password) {
        CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest(name, user, multiplayer, password);
        eventBus.post(createLobbyRequest);
    }

    /**
     * Posts a request to join a specified lobby on the EventBus
     *
     * @param name Name of the lobby the user wants to join
     * @param user User who wants to join the lobby
     * @see de.uol.swp.common.lobby.request.LobbyJoinUserRequest
     * @author Moritz Scheer
     * @since 2022-11-27
     */
    public void joinLobby(String name, UserDTO user, String password) {
        LobbyJoinUserRequest joinUserRequest = new LobbyJoinUserRequest(name, user, password);
        eventBus.post(joinUserRequest);
    }

    /**
     * Posts a request to leave a specified lobby on the EventBus
     *
     * @param name Name of the lobby the user wants to join
     * @param user User who wants to join the lobby
     * @param lobbyID To identify the lobby with a unique key
     * @param multiplayer Boolean value to query if the user is in the multiplayer
     * @see de.uol.swp.common.lobby.request.LobbyLeaveUserRequest
     * @author Daniel Merzo, Moritz Scheer
     * @since 2022-12-15
     */
    public void leaveLobby(String name, UserDTO user, Integer lobbyID, Boolean multiplayer){
        LobbyLeaveUserRequest leaveUserRequest = new LobbyLeaveUserRequest(name, user, lobbyID, multiplayer);
        eventBus.post(leaveUserRequest);
    }


    /**
     * Posts a request to retrieve all lobbies on the EventBus
     *
     * @see de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public void retrieveAllLobbies() {
        RetrieveAllOnlineLobbiesRequest retrieveAllLobbiesRequest = new RetrieveAllOnlineLobbiesRequest();
        eventBus.post(retrieveAllLobbiesRequest);
    }
}
