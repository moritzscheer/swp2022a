package de.uol.swp.common.lobby.chat;

import java.util.Date;

public class TextChatMessage {
    private String message;
    private String senderString;
    private Date sendTime;

    public TextChatMessage(String message, String senderString) {
        this.message = message;
        this.senderString = senderString;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderString() {
        return senderString;
    }
}
