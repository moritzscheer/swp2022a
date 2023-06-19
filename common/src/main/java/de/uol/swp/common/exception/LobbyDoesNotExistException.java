package de.uol.swp.common.exception;

public class LobbyDoesNotExistException extends Exception  {

    protected int lobbyID;

    // Constructor
    public LobbyDoesNotExistException(String message, int lobbyID) {
        super(message);
        this.lobbyID = lobbyID;
    }
}
