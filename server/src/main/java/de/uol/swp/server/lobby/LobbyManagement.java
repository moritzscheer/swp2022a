package de.uol.swp.server.lobby;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import java.util.*;

/**
 * Manages creation, deletion and storing of lobbies
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.lobby.dto.LobbyDTO
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class LobbyManagement {
    private String name;
    private Integer lobbyID = 1;
    private final Map<Integer, LobbyDTO> lobbies = new HashMap<>();

    /**
     * Creates a new lobby and adds it to the list, if isMultiplayer is true. Else the helper method
     * createSinglePlayerName is beeing called, which creates an unique Singleplayer Name containing:
     * (name of the owner)-Singleplayer-(counter)
     *
     * @param name          the name of the lobby to create
     * @param owner         the user who wants to create a lobby
     * @param isMultiplayer true if multiplayer, false if singleplayer
     * @throws IllegalArgumentException name already taken
     * @implNote the primary key of the lobbies is the name therefore the name has to be unique
     * @see de.uol.swp.common.user.User
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-17
     */
    public void createLobby(String name, UserDTO owner, String password, Boolean isMultiplayer) {
        while (lobbies.containsKey(lobbyID)) {lobbyID++;}

        for (Map.Entry<Integer, LobbyDTO> entry : lobbies.entrySet()) {
            if (entry.getValue().getName() != null) {
                if (entry.getValue().getName().equals(name)) {
                    throw new IllegalArgumentException("Lobby name " + name + " already exists!");
                }
            }
        }
        lobbies.put(lobbyID, new LobbyDTO(lobbyID, name, owner, password, isMultiplayer));
        this.name = name;
    }

    /**
     * Deletes lobby with requested name
     *
     * @param name String containing the name of the lobby to delete
     * @throws IllegalArgumentException there exists no lobby with the  requested
     *                                  name
     * @since 2019-10-08
     */
    public void dropLobby(String name) {
        if (!lobbies.containsKey(name)) {
            throw new IllegalArgumentException("Lobby name " + name + " not found!");
        }
        lobbies.remove(name);
    }

    /**
     * Searches for the lobby with the requested name
     *
     * @param name String containing the name of the lobby to search for
     * @return either empty Optional or Optional containing the lobby
     * @see Optional
     * @author Moritz Scheer
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
     * getter for the current lobbyID
     *
     * @return Integer Value containing the current lobbyID
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public Integer getlobbyID() {
        return this.lobbyID;
    }

    /**
     * getter for the current lobbyName
     *
     * @return String Value containing the current lobbyName
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the lobby List
     *
     * @return Map<Integer, LobbyDTO> containing all the open lobbies
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public Map<Integer, LobbyDTO> getLobbies() {
        return this.lobbies;
    }
}
