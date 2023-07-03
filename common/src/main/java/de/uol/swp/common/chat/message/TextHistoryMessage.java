package de.uol.swp.common.chat.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
/**
 * Class used to send information about game related actions to display in the GameView
 *
 * @author Waldemar Kempel
 * @since 2023-06-02
 */
public class TextHistoryMessage extends AbstractLobbyMessage {

    private final int lobbyID;

    private final String message;
    /**
     * Constructor of the TextHistoryMessage class
     *
     * @param lobbyID ID of the lobby
     * @param message content of the message
     *
     * @author Waldemar Kempel
     * @since 2022-06-02
     */
    public TextHistoryMessage(int lobbyID, String message) {
        this.message = message;
        this.lobbyID = lobbyID;
    }
    /**
     * Gets the message as string
     *
     * @author Waldemar Kempel
     * @since 2022-06-02
     */
    public String getMessage() {
        return message;
    }
    /**
     * Gets the lobby id
     *
     * @author Waldemar Kempel
     * @since 2022-06-02
     */
    public int getLobbyID() {
        return lobbyID;
    }
}
