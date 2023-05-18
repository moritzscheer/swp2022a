package de.uol.swp.common.game.message;

import de.uol.swp.common.message.AbstractServerMessage;
import de.uol.swp.common.user.Session;

import java.util.List;
import java.util.Objects;

public class PlayerIsReadyMessage extends AbstractServerMessage {
    private static final long serialVersionUID = -6003337260190748189L;
    private final boolean isReady;

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    public PlayerIsReadyMessage(boolean isReady) {
        this.isReady = isReady;
    }

    /**
     * @author Ole Zimmermann
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerIsReadyMessage that = (PlayerIsReadyMessage) o;
        return Objects.equals(isReady, that.isReady);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
