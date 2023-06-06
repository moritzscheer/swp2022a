package de.uol.swp.common.game.message;

import de.uol.swp.common.message.AbstractServerMessage;
import de.uol.swp.common.user.Session;
import de.uol.swp.common.user.UserDTO;

import java.util.List;
import java.util.Objects;

public class PlayerIsReadyMessage extends AbstractServerMessage {
    private static final long serialVersionUID = -6003337260190748189L;
    //    private final boolean isReady;
    private final UserDTO playerIsReady;
    private final int lobbyID;

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    public PlayerIsReadyMessage(UserDTO playerIsReady, int lobbyID) {
        //        this.isReady = isReady;
        this.playerIsReady = playerIsReady;
        this.lobbyID = lobbyID;
    }

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    //    public boolean isReady() {
    //        return isReady;
    //    }
    public UserDTO getPlayerIsReady() {
        return playerIsReady;
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
        return Objects.equals(playerIsReady, that.playerIsReady);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    /**
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public int getLobbyID() {
        return lobbyID;
    }
}
