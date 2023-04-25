package de.uol.swp.common.user.message;

import de.uol.swp.common.message.AbstractServerMessage;
import de.uol.swp.common.user.Session;

import java.util.List;

public class PlayerIsReadyMessage extends AbstractServerMessage {

    private final boolean isReady;

    /**
     * @author OZimmermann
     * @see
     * @since 2023-04-25
     */
    public PlayerIsReadyMessage(boolean isReady) {
        this.isReady = isReady;
    }

    /**
     * @author OZimmermann
     * @see
     * @since 2023-04-25
     */
    public boolean isReady() {
        return isReady;
    }

    @Override
    public void setReceiver(List<Session> receiver) {
        super.setReceiver(receiver);
    }

    @Override
    public List<Session> getReceiver() {
        return super.getReceiver();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
