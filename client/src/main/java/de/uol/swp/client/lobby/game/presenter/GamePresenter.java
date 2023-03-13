package de.uol.swp.client.lobby.game.presenter;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.cards.events.ShowCardsViewEvent;
import de.uol.swp.client.lobby.cards.presenter.CardsPresenter;
import de.uol.swp.client.lobby.game.Tile;
import javafx.application.Platform;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.inject.Inject;



@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(CardsPresenter.class);

    private Integer lobbyID;
    @FXML private GridPane board;
    @FXML private Text player2Ready;
    @FXML private Text player3Ready;
    @FXML private Text player4Ready;
    @FXML private Text player5Ready;
    @FXML private Text player6Ready;
    @FXML private Text player7Ready;
    @FXML private Text player8Ready;
    @FXML private Text player2Name;
    @FXML private Text player3Name;
    @FXML private Text player4Name;
    @FXML private Text player5Name;
    @FXML private Text player6Name;
    @FXML private Text player7Name;
    @FXML private Text player8Name;

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


        Platform.runLater(() -> {
            setAllPlayersNotReady();
        });
    }
    private void setAllPlayersNotReady(){
        player2Ready.setText("Not ready");
        player2Ready.setFill(Color.RED);
        player3Ready.setText("Not ready");
        player3Ready.setFill(Color.RED);
        player4Ready.setText("Not ready");
        player4Ready.setFill(Color.RED);
        player5Ready.setText("Not ready");
        player5Ready.setFill(Color.RED);
        player6Ready.setText("Not ready");
        player6Ready.setFill(Color.RED);
        player7Ready.setText("Not ready");
        player7Ready.setFill(Color.RED);
        player8Ready.setText("Not ready");
        player8Ready.setFill(Color.RED);
    }
    @FXML
    private void onSubmit(MouseEvent mouseEvent) {
        eventBus.post(new ShowCardsViewEvent(lobbyID));
    }

    public void addTiles() {

    }

}
