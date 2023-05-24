package de.uol.swp.common.game.dto;

import de.uol.swp.common.user.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Object to transfer the information of a Player
 *
 * This object is used to communicate the current state of the game players between the server and
 * clients. It contains information about the
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-13
 */
public class PlayerDTO implements Serializable {
    private RobotDTO robotDTO;
    private List<CardDTO> currentCards;

    private UserDTO user = null; // this only in the case it is a real player

    /** Constructor
     *
     * @param robotDTO robot that belongs to this Player
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @author Maria Eduarda Costa Leite Andrade
     */
    public PlayerDTO(RobotDTO robotDTO){
        this.robotDTO = robotDTO;
    }


    /**
     * Getter for RobotDTO
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @since 2023-05-13
     */
    public RobotDTO getRobotDTO() {
        return robotDTO;
    }

    /**
     * Setter for RobotDTO
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @since 2023-05-13
     */
    public void setRobotDTO(RobotDTO robotDTO) {
        this.robotDTO = robotDTO;
    }

    /**
     * Getter for current Cards, chosen or given
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.CardDTO
     * @since 2023-05-13
     */
    public List<CardDTO> getCurrentCards() {
        return currentCards;
    }

    /**
     * Setter for current Cards, chosen or given
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.CardDTO
     * @since 2023-05-13
     */
    public void setCurrentCards(List<CardDTO> currentCards) {
        this.currentCards = currentCards;
    }

    /**
     * Getter for user that controls this player
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.user.UserDTO
     * @since 2023-05-13
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Setter for user that controls this player
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.user.UserDTO
     * @since 2023-05-13
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }
}