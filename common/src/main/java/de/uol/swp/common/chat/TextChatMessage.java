package de.uol.swp.common.chat;

public class TextChatMessage {
    private final String message;
    private final String senderString;

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
