package de.uol.swp.client.lobby.game.presenter;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.cards.events.ShowCardsViewEvent;
import de.uol.swp.client.lobby.cards.presenter.CardsPresenter;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(CardsPresenter.class);

    private Integer lobbyID;

    public GamePresenter() { }

    public void init(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    @FXML
    private void onSubmit(MouseEvent mouseEvent) {
        eventBus.post(new ShowCardsViewEvent(lobbyID));
    }
}
