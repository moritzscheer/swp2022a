package de.uol.swp.server.lobby;

import de.uol.swp.common.lobby.Lobby;
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
public class LobbyManagement {
    private Integer lobbyID = 1;
    private Integer currentLobbyID;
    private final Map<Integer, LobbyDTO> lobbies = new HashMap<>();

    /**
     * Creates a new lobby and adds it to the list, if isMultiplayer is true. Else the helper method
     * createSinglePlayerName is being called, which creates a unique Singleplayer Name containing:
     * (name of the owner)-Singleplayer-(counter)
     *
     * @param name          the name of the lobby to create
     * @param owner         the user who wants to create a lobby
     * @param isMultiplayer true if multiplayer, false if singleplayer
     * @throws IllegalArgumentException name already taken
     * @implNote the primary key of the lobbies is the name therefore the name has to be unique
     * @author Moritz Scheer & Maxim Erden
     * @see de.uol.swp.common.user.User
     * @since 2022-11-17
     */
    public void createLobby(String name, UserDTO owner, String password, Boolean isMultiplayer) {
        while (lobbies.containsKey(lobbyID)) {lobbyID++;}

        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            if (entry.getValue().getName() != null && entry.getValue().getName().equals(name)) {
                throw new IllegalArgumentException("Lobby name " + name + " already exists!");
            }
        }

        UUID textChannelUUID = null;
        if(TextChatService.getInstance() != null) {
            textChannelUUID = TextChatService.getInstance().createTextChatChannel();
            TextChatService.getInstance().joinUser(textChannelUUID, owner);
        }

        lobbies.put(
                lobbyID,
                new LobbyDTO(lobbyID, name, owner, password, isMultiplayer, textChannelUUID));
        this.currentLobbyID = lobbyID;
        this.lobbyID = 1;
    }

    /**
     * Deletes lobby with requested lobbyID
     *
     * @param lobbyID Integer containing the name of the lobby to delete
     * @author Daniel Merzo, Moritz Scheer
     * @since 2019-10-08
     */
    public void dropLobby(Integer lobbyID) {
        if(TextChatService.getInstance() != null && lobbies.get(lobbyID).getTextChatID() != null) {
            TextChatService.getInstance().closeTextChatChannel(lobbies.get(lobbyID).getTextChatID());
        }
        lobbies.remove(lobbyID);
    }

    /**
     * Searches for the lobby with the requested name
     *
     * @param name String containing the name of the lobby to search for
     * @return either empty Optional or Optional containing the lobby
     * @author Moritz Scheer
     * @see Optional
     * @since 2019-10-08
     */
    public Optional<Lobby> getLobby(String name) {
        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            if (entry.getValue().getName() != null && entry.getValue().getName().equals(name)) {
                Lobby lobby = lobbies.get(entry.getKey());
                return Optional.of(lobby);
            }
        }
        return Optional.empty();
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
    public Optional<LobbyDTO> getLobby(Integer lobbyID) {
        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            if (entry.getKey().equals(lobbyID)) {
                LobbyDTO lobby = lobbies.get(entry.getKey());
                return Optional.of(lobby);
            }
        }
        return Optional.empty();
    }

    /**
     * getter for the current lobbyID
     *
     * @return Integer Value containing the current lobbyID
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public Integer getCurrentLobbyID() {
        return this.currentLobbyID;
    }

    /**
     * getter for all lobby
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
     * getter for the lobby List
     *
     * @return List<LobbyDTO> containing all the open multiplayer lobbies
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public List<LobbyDTO> getMultiplayerLobbies() {
        List<LobbyDTO> list = new ArrayList<>();

        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            if (entry.getValue().isMultiplayer()) {
                list.add(entry.getValue().createWithoutUserPassword(entry.getValue()));
            }
        }
        return list;
    }
}
