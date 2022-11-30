package de.uol.swp.common.lobby.dto;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.user.User;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Object to transfer the information of a game lobby
 *
 * This object is used to communicate the current state of game lobbies between
 * the server and clients. It contains information about the Name of the lobby,
 * who owns the lobby and who joined the lobby.
 *
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class LobbyDTO implements Lobby {

    private final Integer lobbyID;
    private final String name;
    private User owner;
    private final Set<User> users = new TreeSet<>();
    private final String password;
    private final Boolean multiplayer;
    private final Integer playerSlot = 8;

    /**
     * Constructor
     *
     * @param name    The name the lobby should have
     * @param creator The user who created the lobby and therefore shall be the
     *                owner
     * @since 2019-10-08
     */
    public LobbyDTO(Integer lobbyID, String name, User creator, String password, Boolean multiplayer) {
        this.lobbyID = lobbyID;
        this.name = name;
        this.owner = creator;
        this.users.add(creator);
        this.password = password;
        this.multiplayer = multiplayer;
    }

    /**
     * Copy constructor leaving password variable empty
     *
     * This constructor is used for the lobby list, because it would be a major security
     * flaw to send all lobby data including passwords to everyone.
     *
     * @param lobby Lobby object to copy the values of
     * @return LobbyDTO copy of Lobby object having the password variable left empty
     * @since 2022-11-30
     */
    public static LobbyDTO createWithoutPassword(Lobby lobby) {
        return new LobbyDTO(lobby.getLobbyID(), lobby.getName(), lobby.getOwner(), null, true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void joinUser(User user, String password) {
        if(isMultiplayer()) {
            if(users.size() >= playerSlot) {
                throw new IllegalArgumentException("Lobby is already full!");
            } else if(password.equals(this.password)) {
                this.users.add(user);
            } else {
                throw new IllegalArgumentException("password is incorrect!");
            }
        } else {
            throw new IllegalArgumentException("Lobby is set to Private!");
        }
    }

    @Override
    public void leaveUser(User user) {
        if (users.size() == 1) {
            throw new IllegalArgumentException("Lobby must contain at least one user!");
        }
        if (users.contains(user)) {
            this.users.remove(user);
            if (this.owner.equals(user)) {
                updateOwner(users.iterator().next());
            }
        }
    }

    @Override
    public void updateOwner(User user) {
        if (!this.users.contains(user)) {
            throw new IllegalArgumentException("User " + user.getUsername() + "not found. Owner must be member of lobby!");
        }
        this.owner = user;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Boolean isMultiplayer() {
        return this.multiplayer;
    }

    public Integer getLobbyID() {
        return this.lobbyID;
    }
}
