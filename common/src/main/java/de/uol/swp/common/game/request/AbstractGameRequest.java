package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.message.AbstractRequestMessage;

import java.util.Objects;

/**
 * Base class of all game request messages. Basic handling of game data.
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-01
 */
public class AbstractGameRequest extends AbstractRequestMessage {
    String name;
    LobbyDTO lobby;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2023-05-01
     */
    public AbstractGameRequest() {}

    /**
     * Constructor
     *
     * @param name name of the game
     * @param lobby lobby responsible for the creation of this message
     * @since 2023-05-01
     */
    public AbstractGameRequest(String name, LobbyDTO lobby) {
        if (name == null) {
            throw new NullPointerException("name can not be null");
        }
        if(
                name.length() < 3 ||
                        name.length() > 20 ||
                        !name.matches("^[a-zA-Z0-9]*$")
        ) {
            throw new IllegalArgumentException("Name must be between 3 and 20 characters long and can only contain letters and numbers");
        }
        if (lobby == null) {
            throw new NullPointerException("lobby can not be null");
        }
        this.name = name;
        this.lobby = lobby;
    }

    /**
     * Getter for the name variable
     *
     * @return String containing the game's name
     * @since 2023-05-01
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name variable
     *
     * @param name String containing the game's name
     * @since 2023-05-01
     */
    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("name can not be null");
        }
        this.name = name;
    }

    /**
     * Getter for the lobby variable
     *
     * @return Lobby responsible for the creation of this message
     * @since 2023-05-01
     */
    public LobbyDTO getLobby() {
        return this.lobby;
    }

    /**
     * Setter for the lobby variable
     *
     * @param lobby Lobby responsible for the creation of this message
     * @since 2023-05-01
     */
    public void setLobby(LobbyDTO lobby) {
        if (lobby == null) {
            throw new NullPointerException("lobby can not be null");
        }
        this.lobby = lobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractGameRequest that = (AbstractGameRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(lobby, that.lobby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lobby);
    }
}
