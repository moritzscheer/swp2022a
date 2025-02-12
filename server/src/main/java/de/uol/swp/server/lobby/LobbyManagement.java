package de.uol.swp.server.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.chat.TextChatService;

import java.util.*;

/**
 * Manages creation, deletion and storing of lobbies
 *
 * @author Marco Grawunder
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.lobby.dto.LobbyDTO
 * @since 2019-10-08
 */
@Singleton
public class LobbyManagement {
    private final Map<Integer, LobbyDTO> lobbies = new HashMap<>();

    @Inject
    public LobbyManagement() {}

    /**
     * Creates a new lobby and adds it to the list, if isMultiplayer is true. Else the helper method
     * createSinglePlayerName is being called, which creates a unique Singleplayer Name containing:
     * (name of the owner)-Singleplayer-(counter)
     *
     * @param name the name of the lobby to create
     * @param owner the user who wants to create a lobby
     * @param multiplayer true if multiplayer, false if singleplayer
     * @throws IllegalArgumentException name already taken
     * @implNote the primary key of the lobbies is the name therefore the name has to be unique
     * @author Moritz Scheer & Maxim Erden
     * @see de.uol.swp.common.user.User
     * @since 2022-11-17
     */
    public int createLobby(String name, UserDTO owner, String password, Boolean multiplayer) {
        Random random = new Random();

        int lobbyID = random.nextInt(10000000) + 1;
        while (lobbies.containsKey(lobbyID)) {
            lobbyID = random.nextInt(10000000) + 1;
        }

        // check if name already exists
        if (multiplayer) {
            for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
                if (entry.getValue().getName() != null && entry.getValue().getName().equals(name)) {
                    throw new IllegalArgumentException("Lobby name " + name + " already exists!");
                }
            }
        }

        UUID textChannelUUID = null;
        if (TextChatService.getInstance() != null) {
            textChannelUUID = TextChatService.getInstance().createTextChatChannel();
            TextChatService.getInstance().joinUser(textChannelUUID, owner);
        }

        lobbies.put(
                lobbyID,
                new LobbyDTO(lobbyID, name, owner, password, multiplayer, textChannelUUID, false));
        return lobbyID;
    }

    /**
     * Deletes lobby with requested lobbyID
     *
     * @param lobbyID Integer containing the name of the lobby to delete
     * @author Daniel Merzo, Moritz Scheer
     * @since 2019-10-08
     */
    public void dropLobby(Integer lobbyID) {
        if (TextChatService.getInstance() != null && lobbies.get(lobbyID).getTextChatID() != null) {
            TextChatService.getInstance()
                    .closeTextChatChannel(lobbies.get(lobbyID).getTextChatID());
        }
        lobbies.remove(lobbyID);
    }

    /**
     * Searches for the lobby with the requested lobbyID
     *
     * @param lobbyID Integer containing the lobbyID of the lobby to search for
     * @return either empty Optional or Optional containing the lobby
     * @author Moritz Scheer
     * @see Optional
     * @since 2022-12-13
     */
    public Optional<LobbyDTO> getLobby(int lobbyID) {
        if (!lobbies.containsKey(lobbyID)) {
            System.out.println(lobbyID + " could not be found");
            return Optional.empty();
        }
        return Optional.of(lobbies.get(lobbyID));
    }

    /**
     * Getter for all open lobbies
     *
     * @return List<LobbyDTO> containing all the open lobbies
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public List<LobbyDTO> getLobbies() {
        List<LobbyDTO> list = new ArrayList<>();

        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            list.add(entry.getValue().createWithoutUserPassword(entry.getValue()));
        }
        return list;
    }

    /**
     * Getter for the list of open multiplayer lobbies
     *
     * @return List<LobbyDTO> containing all the open multiplayer lobbies
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public List<LobbyDTO> getMultiplayerLobbies() {
        List<LobbyDTO> list = new ArrayList<>();

        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            if (entry.getValue().isMultiplayer() && !entry.getValue().isLobbyStarted()) {
                list.add(entry.getValue().createWithoutUserPassword(entry.getValue()));
            }
        }
        return list;
    }
}
