package de.uol.swp.server.gamelogic.moves;

import de.uol.swp.common.game.dto.PlayerDTO;

import java.util.List;
import java.util.Objects;


/**
 * HelperClass to send message to client to show which movement happened
 *
 * With this class it is also now sent the movements according to the
 * priority of the cards
 *
 * @author Maria Andrade
 * @since 2023-06-20
 */
public class GameMovement {
    private String moveMessage;
    private final List<PlayerDTO> robotsPositionsInOneMove;

    private final GameMovement oldState;



    public GameMovement(List<PlayerDTO> robotsPositionsInOneMove, String moveType, GameMovement oldState,
                        String robotThatPlayedTheCard) {
        this.robotsPositionsInOneMove = robotsPositionsInOneMove;
        this.oldState = oldState;

        if(!Objects.equals(moveType, null)){
            this.moveMessage = "[" + moveType + "] "+ robotThatPlayedTheCard + "\n";
            if(!Objects.equals(this.oldState, null)){
                createMessageOfRobotsThatMoved();
            }
        }
        else
            this.moveMessage = "";


    }

    public List<PlayerDTO> getRobotsPositionsInOneMove() {
        return robotsPositionsInOneMove;
    }

    public String getMoveMessage() {
        return moveMessage;
    }

    private void createMessageOfRobotsThatMoved(){
        for (int i = 0; i < oldState.getRobotsPositionsInOneMove().size(); i++) {
            PlayerDTO oldPlayerDTO = oldState.getRobotsPositionsInOneMove().get(i);
            PlayerDTO newPlayerDTO = this.robotsPositionsInOneMove.get(i);
            assert oldPlayerDTO.getUser().equals(newPlayerDTO.getUser());

            if (!oldPlayerDTO.getRobotDTO().equals(newPlayerDTO.getRobotDTO())) {
                this.moveMessage = this.moveMessage +
                        "    " + oldPlayerDTO.getUser().getUsername() // User ("
                        + " ("
                        + oldPlayerDTO.getRobotDTO().getPosition().x
                        + ", "
                        + oldPlayerDTO.getRobotDTO().getPosition().y
                        + ")" // old x and y position
                        + "{"
                        + oldPlayerDTO.getRobotDTO().getDirection()
                        + "} " // old direction
                        + // CardType
                        "to" //
                        + " ("
                        + newPlayerDTO.getRobotDTO().getPosition().x
                        + ", "
                        + newPlayerDTO.getRobotDTO().getPosition().y
                        + ")" // new x and y position
                        + "{"
                        + newPlayerDTO.getRobotDTO().getDirection()
                        + "} \n"; // new direction
            }
            if (oldPlayerDTO.getRobotDTO().isAlive() & !newPlayerDTO.getRobotDTO().isAlive()) {
                this.moveMessage = this.moveMessage +
                        "    " + oldPlayerDTO.getUser().getUsername() // User ("
                        + " died\n";
            }
        }
    }
}