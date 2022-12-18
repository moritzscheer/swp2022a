package de.uol.swp.server.lobby;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private final Map<Integer, Lobby> lobbies = new HashMap<>();

    /**
     * Creates a new lobby and adds it to the list, if isMultiplayer is true. Else the helper method
     * createSinglePlayerName is beeing called, which creates an unique Singleplayer Name containing:
     * (name of the owner)-Singleplayer-(counter)
     *
     * @implNote the primary key of the lobbies is the name therefore the name has to be unique
     *
     * @param name the name of the lobby to create
     * @param owner the user who wants to create a lobby
     * @param isMultiplayer true if multiplayer, false if singleplayer
     * @see de.uol.swp.common.user.User
     * @throws IllegalArgumentException name already taken
     * @since 2022-11-17
     */
    public void createLobby(String name, UserDTO owner, String password, Boolean isMultiplayer) {
        while (lobbies.containsKey(lobbyID)) {lobbyID++;}

        for (Map.Entry<Integer, Lobby> entry : lobbies.entrySet()) {
            if(entry.getValue().getName() != null) {
                if (entry.getValue().getName().equals(name)) {
                    throw new IllegalArgumentException("Lobby name " + name + " already exists!");
                }
            }
        }
        lobbies.put(lobbyID, new LobbyDTO(name, owner, password, true));
        this.name = name;
    }

    /**
     * Deletes lobby with requested lobbyID
     *
     * @param lobbyID Integer containing the name of the lobby to delete
     * @author Daniel Merzo, Moritz Scheer
     * @since 2019-10-08
     */
    public void dropLobby(Integer lobbyID) {
        lobbies.remove(lobbyID);
    }

    /**
     * Searches for the lobby with the requested name
     *
     * @param name String containing the name of the lobby to search for
     * @return either empty Optional or Optional containing the lobby
     * @see Optional
     * @since 2019-10-08
     */
    public Optional<Lobby> getLobby(String name) {
        Lobby lobby = lobbies.get(name);
        if (lobby != null) {
            return Optional.of(lobby);
        }
        return Optional.empty();
    }

    public Integer getlobbyID() {
        return this.lobbyID;
    }

    public String getName() {
        return this.name;
    }

}
