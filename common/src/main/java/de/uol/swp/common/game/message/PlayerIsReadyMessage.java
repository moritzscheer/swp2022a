package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.Session;
import de.uol.swp.common.user.UserDTO;

import java.util.List;
import java.util.Objects;

/**
 * Message sent by the server when a player has chosen cards
 *
 * @see AbstractLobbyMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-04-25
 */
public class PlayerIsReadyMessage extends AbstractLobbyMessage {
    private static final long serialVersionUID = -6003337260190748189L;
    private final UserDTO playerIsReady;
    private final int lobbyID;

    /**
     * Constructor
     *
     * @param playerIsReady UserDTO of the player who is ready
     * @param lobbyID lobby ID of the lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-19
     */
    public PlayerIsReadyMessage(UserDTO playerIsReady, int lobbyID) {
        this.playerIsReady = playerIsReady;
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the ready player
     *
     * @return The UserDTO of the player who is ready
     * @author Ole Zimmermann
     * @since 2023-06-06
     */
    public UserDTO getPlayerIsReady() {
        return playerIsReady;
    }
    /**
     * Setter for the receiver List
     *
     * @param receiver List of Sessions
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    @Override
    public void setReceiver(List<Session> receiver) {
        super.setReceiver(receiver);
    }
    /**
     * Getter for the receiver List
     *
     * @return List of sessions
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    @Override
    public List<Session> getReceiver() {
        return super.getReceiver();
    }
    /**
     * Overrides the equals method to compare the objects attributes
     *
     * @return boolean if equals or not
     * @param o Object
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerIsReadyMessage that = (PlayerIsReadyMessage) o;
        return Objects.equals(playerIsReady, that.playerIsReady);
    }
    /**
     * Overrides the hashCode method
     *
     * @return hashCode of super class
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    /**
     * Getter for the lobby id
     *
     * @exception IllegalArgumentException if lobby id is negative
     * @return lobby id
     * @author Maria Eduarda Costa Leite Andrade, Waldemar Kempel
     * @since 2023-05-13
     */
    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
}
