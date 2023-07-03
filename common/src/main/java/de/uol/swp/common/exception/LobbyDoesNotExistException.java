package de.uol.swp.common.exception;

/**
 * Exception to state e.g. that a lobby does not exist
 *
 * @author Maria Andrade
 * @since 2023-06-18
 */
public class LobbyDoesNotExistException extends Exception {

    protected int lobbyID;

    /**
     * Constructor
     *
     * @param lobbyID the not existing lobby id
     * @param message content of the exception
     * @author Maria Andrade
     * @since 2023-06-18
     */
    public LobbyDoesNotExistException(String message, int lobbyID) {
        super(message);
        this.lobbyID = lobbyID;
    }
}
