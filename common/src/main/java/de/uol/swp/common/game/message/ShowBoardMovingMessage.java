package de.uol.swp.common.game.message;

import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

import java.util.List;

/**
 * Message sent by the server when there is an update of the board positions
 *
 * @see AbstractLobbyMessage
 * @author Maxim Erden
 * @since 2023-06-06
 */
public class ShowBoardMovingMessage extends AbstractLobbyMessage {
    private final int lobbyID;
    // current position and directions and users dto are within playersDTO
    private final List<PlayerDTO> playersDTO;
    /**
     * Constructor
     *
     * @author Maxim Erden
     * @param lobbyID integer
     * @param playersDTO List of playerDTO
     * @since 2023-06-06
     */
    public ShowBoardMovingMessage(int lobbyID, List<PlayerDTO> playersDTO) {
        this.lobbyID = lobbyID;
        this.playersDTO = playersDTO;
    }
    /**
     * Getter of the lobby id
     *
     * @author Maxim Erden
     * @return lobby id as integer
     * @exception IllegalArgumentException if lobby id is negative
     * @since 2023-06-06
     */
    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
    /**
     * Getter of the playerDTOs
     *
     * @author Maxim Erden
     * @return list of playerDTOs
     * @since 2023-06-06
     */
    public List<PlayerDTO> getPlayersDTO() {
        return playersDTO;
    }
}
