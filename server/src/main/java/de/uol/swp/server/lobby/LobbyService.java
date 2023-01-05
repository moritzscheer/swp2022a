package de.uol.swp.server.lobby;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse;
import de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse;
import de.uol.swp.common.lobby.message.*;
import de.uol.swp.common.lobby.request.*;
import de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse;
import de.uol.swp.common.lobby.response.*;
import de.uol.swp.common.message.ResponseMessage;
import de.uol.swp.common.message.ServerMessage;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Handles the lobby requests send by the users
 *
 * @author Marco Grawunder
 * @since 2019-10-08
 */
@SuppressWarnings("UnstableApiUsage")
@Singleton
public class LobbyService extends AbstractService {

    private static final Logger LOG = LogManager.getLogger(LobbyService.class);
    private final LobbyManagement lobbyManagement;
    private final AuthenticationService authenticationService;

    /**
     * Constructor
     *
     * @param lobbyManagement The management class for creating, storing and deleting
     *                        lobbies
     * @param authenticationService the user management
     * @param eventBus the server-wide EventBus
     * @since 2019-10-08
     */
    @Inject
    public LobbyService(LobbyManagement lobbyManagement, AuthenticationService authenticationService, EventBus eventBus) {
        super(eventBus);
        this.lobbyManagement = lobbyManagement;
        this.authenticationService = authenticationService;
    }

    /**
     * Handles CreateLobbyRequests found on the EventBus
     *
     * If a CreateLobbyRequest is detected on the EventBus, this method is called.
     * It creates a new Lobby via the LobbyManagement using the parameters from the
     * request and sends a LobbyCreatedMessage to every connected user, if the isMultiplayer variable
     * is set to true. Also, a LobbyCreatedResponse is sent to the Client that send the Request.
     *
     * @param createLobbyRequest The CreateLobbyRequest found on the EventBus
     * @see de.uol.swp.server.lobby.LobbyManagement#createLobby(String, UserDTO, String, Boolean)
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see UserCreatedLobbyMessage
     * @see de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse
     * @see de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse
     * @author Moritz Scheer
     * @since 2022-11-24
     */
    @Subscribe
    public void onCreateLobbyRequest(CreateLobbyRequest createLobbyRequest) {
        ResponseMessage returnMessage;
        try {
            lobbyManagement.createLobby(createLobbyRequest.getName(), createLobbyRequest.getUser(), createLobbyRequest.getPassword(), createLobbyRequest.isMultiplayer());

            //sends a message to all clients (for the lobby list) and sends a response to the client that send the request
            if(createLobbyRequest.isMultiplayer()) {
                sendToAll(new UserCreatedLobbyMessage(lobbyManagement.getLobby(lobbyManagement.getCurrentLobbyID()).get(), (UserDTO) createLobbyRequest.getOwner()));
            }
            returnMessage = new LobbyCreatedSuccessfulResponse(lobbyManagement.getLobby(lobbyManagement.getCurrentLobbyID()).get(), createLobbyRequest.getUser());
        } catch (IllegalArgumentException e) {
            LOG.error(e);
            returnMessage = new LobbyCreatedExceptionResponse("Cannot create Lobby. " + e.getMessage());
        }
        LOG.info("lobby: {} created successfully", createLobbyRequest.getName());
        createLobbyRequest.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
    }

    /**
     * Handles LobbyJoinUserRequests found on the EventBus
     *
     * If a LobbyJoinUserRequest is detected on the EventBus, this method is called.
     * It adds a user to a Lobby stored in the LobbyManagement and sends a UserJoinedLobbyMessage
     * to every user in the lobby and a LobbyJoinedSuccessfulResponse to the Client that send the request.
     * If no lobby was found, the password is wrong or the lobby is full a LobbyJoinedExceptionResponse is sent to the client.
     *
     * @param joinLobbyRequest The LobbyJoinUserRequest found on the EventBus
     * @see de.uol.swp.common.lobby.Lobby
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @author Moritz Scheer & Maxim Erden
     * @since 2019-10-08
     */
    @Subscribe
    public void onLobbyJoinUserRequest(JoinLobbyRequest joinLobbyRequest) {
        Optional<Lobby> lobby = lobbyManagement.getLobby(joinLobbyRequest.getName());

        ResponseMessage returnMessage;
        if (lobby.isPresent()) {
            try {
                lobby.get().joinUser(joinLobbyRequest.getUser(), joinLobbyRequest.getPassword());

                //sends a message to all clients in the lobby (for the player list) and sends a response to the client that send the request
                returnMessage = new LobbyJoinedSuccessfulResponse((LobbyDTO) lobby.get(), joinLobbyRequest.getUser());
                LOG.info("lobby {} joined successfully", lobby.get().getName());
            } catch (IllegalArgumentException e) {
                LOG.error(e);
                returnMessage = new LobbyJoinedExceptionResponse("Cannot join Lobby. " + e.getMessage());
            }
        } else {
            returnMessage = new LobbyJoinedExceptionResponse("Cannot find lobby. Lobby does not exist!");
        }
        joinLobbyRequest.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
        sendToAllInLobby(joinLobbyRequest.getName(), new UserJoinedLobbyMessage(lobby.get().getLobbyID(), joinLobbyRequest.getName(), joinLobbyRequest.getUser()));
    }

    /**
     * Handles LobbyLeaveUserRequests found on the EventBus
     *
     * If a LobbyLeaveUserRequest is detected on the EventBus, this method is called.
     * It removes a user from a Lobby stored in the LobbyManagement and sends a
     * UserLeftLobbyMessage to every user in the lobby.
     *
     * @param leaveLobbyRequest The LobbyJoinUserRequest found on the EventBus
     * @see de.uol.swp.common.lobby.Lobby
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2019-10-08
     */
    @Subscribe
    public void onLobbyLeaveUserRequest(LeaveLobbyRequest leaveLobbyRequest) {
        Optional<Lobby> lobby = lobbyManagement.getLobby(leaveLobbyRequest.getName());

        ResponseMessage returnMessage;
        if (lobby.isPresent()) {
            try {
                lobby.get().leaveUser(leaveLobbyRequest.getUser());

                //sends a message to all clients in the lobby (for the player list) and sends a response to the client that send the request
                if(leaveLobbyRequest.isMultiplayer()) {
                    sendToAllInLobby(leaveLobbyRequest.getName(), new UserLeftLobbyMessage(lobby.get().getLobbyID(), leaveLobbyRequest.getName(), leaveLobbyRequest.getUser(), (UserDTO) lobby.get().getOwner()));
                }
                returnMessage = new LobbyLeftSuccessfulResponse((LobbyDTO) lobby.get(), leaveLobbyRequest.getUser());
            } catch (IllegalArgumentException e) {
                lobbyManagement.dropLobby(leaveLobbyRequest.getLobbyID());

                //sends a message to all clients in the lobby (for the player list) and sends a response to the client that send the request
                if (leaveLobbyRequest.isMultiplayer()) {
                    sendToAll(new UserDroppedLobbyMessage((LobbyDTO) lobby.get(), leaveLobbyRequest.getName(), leaveLobbyRequest.getUser()));
                }
                returnMessage = new LobbyDroppedSuccessfulResponse(leaveLobbyRequest.getName(), leaveLobbyRequest.getUser(), leaveLobbyRequest.getLobbyID());
            }
        } else {
            returnMessage = new LobbyLeftExceptionResponse(lobby.get().getLobbyID(), leaveLobbyRequest.getName(), (UserDTO) leaveLobbyRequest.getUser(), "Cannot find lobby. Lobby does not exist!");
        }
        leaveLobbyRequest.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
    }

    /**
     * Prepares a given ServerMessage to be sent to all players in the lobby and
     * posts it on the EventBus
     *
     * @param lobbyName Name of the lobby the players are in
     * @param message the message to be sent to the users
     * @see de.uol.swp.common.message.ServerMessage
     * @since 2019-10-08
     */
    public void sendToAllInLobby(String lobbyName, ServerMessage message) {
        Optional<Lobby> lobby = lobbyManagement.getLobby(lobbyName);

        if (lobby.isPresent()) {
            message.setReceiver(authenticationService.getSessions(lobby.get().getUsers()));
            post(message);
        }
        // TODO: error handling not existing lobby
    }

    /**
     * Handles RetrieveAllOnlineLobbiesRequest found on the EventBus
     *
     * If a RetrieveAllOnlineLobbiesRequest is detected on the EventBus, this method
     * is called. It posts a AllOnlineLobbiesResponse containing lobby objects for
     * every open lobby on the EvenBus.
     *
     * @param msg RetrieveAllOnlineLobbiesRequest found on the EventBus
     * @see de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest
     * @see de.uol.swp.common.lobby.response.AllOnlineLobbiesResponse
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    @Subscribe
    public void onRetrieveAllOnlineLobbiesRequest(RetrieveAllOnlineLobbiesRequest msg) {
        AllOnlineLobbiesResponse response = new AllOnlineLobbiesResponse(lobbyManagement.getMultiplayerLobbies());
        response.initWithMessage(msg);
        post(response);
    }

}
