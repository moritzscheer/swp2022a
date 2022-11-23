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
    private String lobbyName;
    private final Map<String, Lobby> lobbies = new HashMap<>();

    /**
     * Creates a new lobby and adds it to the list, if isMultiplayer is true. Else the helper method
     * createSinglePlayerName is beeing called, which creates an unique Singleplayer Name containing:
     * (name of the owner)-Singleplayer-(counter)
     *
     * @implNote the primary key of the lobbies is the name therefore the name has to be unique
     *
     * @param lobbyName the name of the lobby to create
     * @param owner the user who wants to create a lobby
     * @param isMultiplayer true if multiplayer, false if singleplayer
     * @see de.uol.swp.common.user.User
     * @throws IllegalArgumentException name already taken
     * @since 2022-11-17
     */
    public void createLobby(String lobbyName, UserDTO owner, String password, Boolean isMultiplayer) {
        if (isMultiplayer) {
            if (lobbies.containsKey(lobbyName)) {
                throw new IllegalArgumentException("Lobby name " + lobbyName + " already exists!");
            } else {
                lobbies.put(lobbyName, new LobbyDTO(lobbyName, owner, password, true));
                this.lobbyName = lobbyName;
                System.out.println("Lobby '" + lobbyName + "' from User '" + owner.getUsername() + "' was created");
            }
        } else {
            this.lobbyName = createSinglePlayerName(owner);
            lobbies.put(this.lobbyName, new LobbyDTO(this.lobbyName, owner, "", false));
            System.out.println("Lobby '" + this.lobbyName + "' from User '" + owner.getUsername() + "' was created");
        }
    }

    /**
     * creates an unique Singleplayer Name containing: (name of the owner)-Singleplayer-(counter)
     *
     * @param owner the user who wants to create a lobby
     * @see de.uol.swp.common.user.User
     * @return created singlePlayerName
     * @since 2022-11-21
     */
    private String createSinglePlayerName(UserDTO owner) {
        int counter = 1;
        String singlePlayerName = owner.getUsername() + "-SinglePlayer-" + counter;

        while(lobbies.containsKey(singlePlayerName)) {
            counter++;
            singlePlayerName = owner.getUsername() + "-SinglePlayer-" + counter;
        }
        return singlePlayerName;
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
     * @since 2019-10-08
     */
    public Optional<Lobby> getLobby(String name) {
        Lobby lobby = lobbies.get(name);
        if (lobby != null) {
            return Optional.of(lobby);
        }
        return Optional.empty();
    }

    public String getLobbyName() {
        return this.lobbyName;
    }

}
