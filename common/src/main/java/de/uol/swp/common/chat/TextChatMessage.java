package de.uol.swp.common.chat;

import java.io.Serializable;

public class TextChatMessage implements Serializable {
    private final String message;
    private final String senderString;
    private final String timeStamp;

    public TextChatMessage(String message, String senderString, String timeStamp) {
        this.message = message;
        this.senderString = senderString;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderString() {
        return senderString;
    }
}
