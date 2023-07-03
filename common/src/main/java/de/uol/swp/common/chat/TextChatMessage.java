package de.uol.swp.common.chat;

import java.io.Serializable;
/**
 * Container class for text message related informations
 *
 * <p>An Object of this class is used in de.uol.swp.common.chat.message.NewTextChatMessageMessage
 *
 * @author Finn Oldeborshuis
 * @since 2023-01.06
 */
public class TextChatMessage implements Serializable {
    private final String message;
    private final String senderString;
    private final String timeStamp;
    /**
     * Constructor of the TextChatMessage class
     *
     * @param message Content of the message
     * @param senderString sender string of the message
     * @param timeStamp Timestamp of the message
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public TextChatMessage(String message, String senderString, String timeStamp) {
        this.message = message;
        this.senderString = senderString;
        this.timeStamp = timeStamp;
    }
    /**
     * Gets the content of the message
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public String getMessage() {
        return message;
    }
    /**
     * Gets the sender string of the message
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public String getSenderString() {
        return senderString;
    }
    /**
     * Gets the timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    public String getTimeStamp() {
        return timeStamp;
    }
}
