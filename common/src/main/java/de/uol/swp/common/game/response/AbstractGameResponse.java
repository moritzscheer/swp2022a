package de.uol.swp.common.game.response;

import de.uol.swp.common.message.AbstractResponseMessage;

import java.util.Objects;

/**
 * Base class of all lobby response messages. Basic handling of lobby data.
 *
 * @see de.uol.swp.common.lobby.Lobby
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-01
 */
public class AbstractGameResponse extends AbstractResponseMessage {

    String name;
    Integer gameID;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2023-05-01
     */
    public AbstractGameResponse() {}

    /**
     * Constructor
     *
     * @param name name of the game
     * @param gameID game responsible for the creation of this message
     * @since 2023-05-01
     */
    public AbstractGameResponse(String name, Integer gameID) {
        this.name = name;
        this.gameID = gameID;
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
        this.name = name;
    }

    /**
     * Getter for the gameID variable
     *
     * @return game responsible for the creation of this message
     * @since 2023-05-01
     */
    public Integer getGameID() {
        return this.gameID;
    }

    /**
     * Setter for the lobby variable
     *
     * @param gameID game responsible for the creation of this message
     * @since 2023-05-01
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractGameResponse that = (AbstractGameResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(gameID, that.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gameID);
    }
}
