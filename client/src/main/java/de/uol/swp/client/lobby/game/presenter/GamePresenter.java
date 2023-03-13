package de.uol.swp.client.lobby.game.presenter;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.cards.events.ShowCardsViewEvent;
import de.uol.swp.client.lobby.cards.presenter.CardsPresenter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.inject.Inject;



@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(CardsPresenter.class);

    private Integer lobbyID;

    @FXML private GridPane frameGameBoard;

    private GridPane gameBoard = new GridPane();
    private int boardSize = 16;

    public GamePresenter() {
    }

    public void init(Integer lobbyID) {
        this.lobbyID = lobbyID;

        double imageSize = frameGameBoard.getPrefHeight();
        imageSize = imageSize / boardSize;

            for(int i = 0; i < boardSize; i++){
                gameBoard.addColumn(i);
                gameBoard.addRow(i);
            }
            for(int col = 0; col < boardSize; col++){
                for(int row = 0; row < boardSize; row++){
                    gameBoard.add(new ImageView(new Image("images/tiles/field.jpg", imageSize,imageSize,true,false)), row, col);
                }
            }


            frameGameBoard.getChildren().add(gameBoard);


        addTiles();
    }
    @FXML
    private void onSubmit(MouseEvent mouseEvent) {
        eventBus.post(new ShowCardsViewEvent(lobbyID));
    }

    public void addTiles() {

    }

}
