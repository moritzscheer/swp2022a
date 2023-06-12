package de.uol.swp.common.lobby.dto;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Object to transfer the information of a game lobby
 *
 * <p>This object is used to communicate the current state of game lobbies between the server and
 * clients. It contains information about the Name of the lobby, who owns the lobby and who joined
 * the lobby.
 *
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class LobbyDTO implements Lobby {

    private final Integer lobbyID;
    private final String name;
    private User owner;
    private final Set<User> users = new TreeSet<>();
    private final Set<User> notReadyUsers = new TreeSet<>();
    private final String password;
    private final Boolean multiplayer;
    private final Integer playerSlot = 8;
    private final UUID chatChannel;

    private int countRequestToStartGame = 0;
    private String mapName;

    private Map currentMap;

    /**
     * Constructor
     *
     * @param lobbyID The id given to the lobby
     * @param name The name the lobby should have
     * @param creator The user who created the lobby and therefore shall be the
     * @param password The password given to the lobby
     * @param multiplayer The gamemode given to the lobby
     * @author Moritz Scheer
     * @since 2023-01-03
     */
    public LobbyDTO(
            Integer lobbyID,
            String name,
            User creator,
            String password,
            Boolean multiplayer,
            UUID chatChannelUUID
            ) {
        this.lobbyID = lobbyID;
        this.name = name;
        this.owner = creator;
        this.users.add(creator);
        this.notReadyUsers.add(creator.getWithoutPassword());
        this.password = password;
        this.multiplayer = multiplayer;
        this.chatChannel = chatChannelUUID;

    }
    public LobbyDTO() {
        this.lobbyID = 0;
        this.name = "";
        this.owner = null;
        this.password = "";
        this.multiplayer = false;
        this.chatChannel = null;
    }

    /**
     * Copy constructor leaving password variable empty
     *
     * <p>This constructor is used for the lobby list, because it would be a major security flaw to
     * send all lobby data including passwords to everyone.
     *
     * @param lobby Lobby object to copy the values of
     * @return LobbyDTO copy of Lobby object having the password variable left empty
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public LobbyDTO createWithoutPassword(Lobby lobby) {
        Lobby tmp = createWithoutUserPassword(lobby);
        if (tmp.getPassword().equals("")) {
            return new LobbyDTO(
                    lobby.getLobbyID(), lobby.getName(), lobby.getOwner(), "", true, null);
        } else {
            String passwordBlurred = "*".repeat(lobby.getPassword().length());
            return new LobbyDTO(
                    lobby.getLobbyID(),
                    lobby.getName(),
                    lobby.getOwner(),
                    passwordBlurred,
                    true,
                    null);
        }
    }

    /**
     * Copy constructor leaving password variable empty of user
     *
     * <p>This constructor is used for the player list in the lobby, because it would be a major
     * security flaw to send all user data including passwords to everyone.
     *
     * @param lobby Lobby object to copy the values of
     * @return LobbyDTO copy of Lobby object having the password variable left empty
     * @author Moritz Scheer
     * @since 2022-12-03
     */
    public LobbyDTO createWithoutUserPassword(Lobby lobby) {
        LobbyDTO tmp =
                new LobbyDTO(
                        lobby.getLobbyID(),
                        lobby.getName(),
                        lobby.getOwner().getWithoutPassword(),
                        lobby.getPassword(),
                        lobby.isMultiplayer(),
                        null);
        for (User users : lobby.getUsers()) {
            if (!users.equals(lobby.getOwner()))
                tmp.joinUser(UserDTO.createWithoutPassword(users), lobby.getPassword());
        }
        return tmp;
    }

    /**
     * Handles User that wants to join the lobby.
     *
     * <p>if the lobby is set to multiplayer, it checks if the right condition is set. If the lobby
     * size is 8 or greater this method throws an IllegalArgumentException e. If the password typed
     * in is correct the User is added to the users Set. Else an exception is thrown. If
     * isMultiplayer is false it throws an IllegalArgumentException e.
     *
     * @param user The User that wants to join the lobby.
     * @param password the password typed in, to join the lobby.
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-27
     */
    @Override
    public void joinUser(User user, String password) {
        if (isMultiplayer()) {
            if (users.size() >= playerSlot) {
                throw new IllegalArgumentException("Lobby is already full!");
            } else if (password.equals(this.password)) {
                this.users.add(user);
                this.notReadyUsers.add(user.getWithoutPassword());
            } else {
                throw new IllegalArgumentException("password is incorrect!");
            }
        } else {
            throw new IllegalArgumentException("Lobby is set to Private!");
        }
    }

    /**
     * Handles User that wants to leave the lobby.
     *
     * <p>If the user that wants to leave the lobby is the last user in the lobby an
     * IllegalArgumentException is thrown. If more than one user is in the lobby, then the user is
     * removed and a new owner is asigned.
     *
     * @param user The User that wants to leave the lobby.
     * @author Moritz Scheer & Daniel Merzo
     * @since 2023-01-04
     */
    @Override
    public void leaveUser(User user) {
        if (users.contains(user)) {
            if (users.size() == 1) {
                throw new IllegalArgumentException("Lobby must contain at least one user!");
            } else {
                this.users.remove(user);
                this.notReadyUsers.remove(user);
                if (this.owner.equals(user)) {
                    updateOwner(users.iterator().next());
                }
            }
        }
    }

    /**
     * Handles updating owner.
     *
     * <p>If the user given from the parameter is not in the lobby, an IllegalArgumentException is
     * thrown. If not the user is set to the current owner of the lobby.
     *
     * @param user containing the User that wants to become the owner
     * @since 2022-12-06
     */
    @Override
    public void updateOwner(User user) {
        if (!this.users.contains(user)) {
            throw new IllegalArgumentException(
                    "User " + user.getUsername() + "not found. Owner must be member of lobby!");
        }
        this.owner = user;
    }

    /**
     * Handles ready players.
     *
     * <p>If a user pressed ready in the lobby the user is removed from the notReadyUsers set
     *
     * @param user containing the User that is ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    @Override
    public void makePlayerReady(User user) {
        this.notReadyUsers.remove(user);
    }

    /**
     * Handles not ready players.
     *
     * <p>If a user pressed not ready in the lobby the user is added to the notReadyUsers set
     *
     * @param user containing the User that is not ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    @Override
    public void makePlayerNotReady(User user) {
        this.notReadyUsers.add(user.getWithoutPassword());
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
     * Getter for the Set of users that are not ready in the lobby
     *
     * @return Set containing the user data in the lobby
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    @Override
    public Set<User> getNotReadyUsers() {
        return Collections.unmodifiableSet(notReadyUsers);
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
    @Override
    public Integer getLobbyID() {
        return this.lobbyID;
    }

    /**
     * Setter for the current map
     *
     * @param m The next Map
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public void setMap(Map m) {
        this.currentMap = m;
    }

    /**
     * Getter for the current Map
     *
     * @return A Map object representing the current map
     * @author Mathis Eilers
     * @since 2022-12-31
     */
    public Map getMap() {
        return this.currentMap;
    }

    public UUID getTextChatID() {
        return chatChannel;
    }

    /**
     * Getter for the countRequestToStartGame
     *
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @return Integer containing the countRequestToStartGame
     * @since 2023-05-01
     */
    public int getCountRequestToStartGame() {
        return this.countRequestToStartGame;
    }

    /**
     * Method to increase the countRequestToStartGame
     *
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @return Integer containing the countRequestToStartGame
     * @since 2023-05-01
     */
    public int increaseCounterRequest() {
        this.countRequestToStartGame += 1;
        return this.countRequestToStartGame;
    }

    /**
     * Method to reset the countRequestToStartGame
     *
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @return Integer containing the countRequestToStartGame
     * @since 2023-05-01
     */
    public int resetCounterRequest() {
        this.countRequestToStartGame = 0;
        return this.countRequestToStartGame;
    }

    public void setMapName(String mapName){
        this.mapName = mapName;
    }
    public String getMapName(){
        return this.mapName;
    }

}
