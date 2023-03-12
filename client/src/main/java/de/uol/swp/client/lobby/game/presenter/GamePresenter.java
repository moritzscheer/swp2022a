package de.uol.swp.client.lobby.game.presenter;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.cards.events.ShowCardsViewEvent;
import de.uol.swp.client.lobby.cards.presenter.CardsPresenter;
import de.uol.swp.client.lobby.game.Tile;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(CardsPresenter.class);

    private Integer lobbyID;
    @FXML private GridPane board;

    public GamePresenter() {
    }

    public void init(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    @FXML
    private void onSubmit(MouseEvent mouseEvent) {
        eventBus.post(new ShowCardsViewEvent(lobbyID));
        addFieldImageToAllTiles();
    }

    /**
     * Add field Image to each Tile
     *
     * @author Tommy Dang
     * @since 2023-03-09
     */
    // Bilder in der richtigen Größe einfügen
    private void addFieldImageToAllTiles(){
        int boardRow = board.getRowCount();
        int boardCol = board.getColumnCount();

        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++){
                Tile fieldTile = new Tile();
                board.add(fieldTile.fieldTile(), col, row);
            }
        }
    }
}
