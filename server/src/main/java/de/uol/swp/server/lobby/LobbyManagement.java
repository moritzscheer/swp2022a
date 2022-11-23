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
    private final Map<String, Lobby> lobbies = new HashMap<>();

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
        if (isMultiplayer) {
            if (lobbies.containsKey(name)) {
                throw new IllegalArgumentException("Lobby name " + name + " already exists!");
            } else {
                this.name = name;
                lobbies.put(name, new LobbyDTO(name, owner, password, true));
                System.out.println("Lobby '" + name + "' from User '" + owner.getUsername() + "' was created");
            }
        } else {
            this.name = createSinglePlayerName(owner);
            lobbies.put(this.name, new LobbyDTO(this.name, owner, "", false));
            System.out.println("Lobby '" + this.name + "' from User '" + owner.getUsername() + "' was created");
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

    public String getName() {
        return this.name;
    }

}
