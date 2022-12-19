package de.uol.swp.common.lobby.dto;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
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
    public LobbyDTO createWithoutPassword(Lobby lobby) {
        createWithoutUserPassword(lobby);
        if(lobby.getPassword() != "") {
            return new LobbyDTO(lobby.getLobbyID(), lobby.getName(), lobby.getOwner(), null, true);
        }
        return new LobbyDTO(lobby.getLobbyID(), lobby.getName(), lobby.getOwner(), lobby.getPassword(), true);
    }

    /**
     * Copy constructor leaving password variable empty of user
     *
     * This constructor is used for the player list in the lobby, because it would be a major security
     * flaw to send all user data including passwords to everyone.
     *
     * @param lobby Lobby object to copy the values of
     * @return LobbyDTO copy of Lobby object having the password variable left empty
     * @since 2022-11-30
     */
    public LobbyDTO createWithoutUserPassword(Lobby lobby) {
        LobbyDTO tmp = new LobbyDTO(lobby.getLobbyID(), lobby.getName(), lobby.getOwner().getWithoutPassword(), lobby.getPassword(), true);
        for (User users : lobby.getUsers()) {
            if(!users.equals(lobby.getOwner()))
                tmp.joinUser(UserDTO.createWithoutPassword(users), lobby.getPassword());
        }
        return tmp;
    }

    /**
     * Getter for the lobby Name
     *
     * @return String containing the lobby Name of the lobby
     * @since 2022-12-06
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles User that wants to join the lobby.
     *
     * if the lobby is set to multiplayer, it checks if the right condition is set. If the lobby size is 8 or greater
     * this method throws an IllegalArgumentException e. If the password typed in is correct the User is added to the
     * users Set. Else an exception is thrown. If isMultiplayer is false it throws an IllegalArgumentException e.
     *
     * @param user The User that wants to join the lobby.
     * @param password the password typed in, to join the lobby.
     * @since 2022-12-01
     */
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

    /**
     * Handles updating owner.
     *
     * If the user given from the parameter is not in the lobby, an IllegalArgumentException is thrown. If not the user
     * is set to the current owner of the lobby.
     *
     * @param user containing the User that wants to become the owner
     * @since 2022-12-06
     */
    @Override
    public void updateOwner(User user) {
        if (!this.users.contains(user)) {
            throw new IllegalArgumentException("User " + user.getUsername() + "not found. Owner must be member of lobby!");
        }
        this.owner = user;
    }

    /**
     * Getter for the current lobby owner
     *
     * @return User containing lobby owner data
     * @since 2022-12-01
     */
    @Override
    public User getOwner() {
        return owner;
    }

    /**
     * Getter for the Set of users
     *
     * @return Set containing the user data in the lobby
     * @since 2022-12-01
     */
    @Override
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Getter for the lobby password
     *
     * @return String containing the lobby password
     * @since 2022-12-01
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter for multiplayer status
     *
     * @return Boolean true if multiplayer, false if singleplayer
     * @since 2022-12-01
     */
    @Override
    public Boolean isMultiplayer() {
        return this.multiplayer;
    }

    /**
     * Getter for the current LobbyID
     *
     * @return Integer containing the ID
     * @since 2022-12-01
     */
    public Integer getLobbyID() {
        return this.lobbyID;
    }
}
