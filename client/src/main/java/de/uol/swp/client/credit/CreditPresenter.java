package de.uol.swp.client.credit;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreditPresenter extends AbstractPresenter {
    /**
     * Manages the credit window
     *
     * @author Tommy Dang
     * @see de.uol.swp.client.AbstractPresenter
     * @since 2022-11-29
     *
     */
    public static final String FXML = "/fxml/CreditView.fxml";
    /**
     * Default Constructor
     *
     * @since 2022-11-29
     */
    public CreditPresenter() {
    }
    @FXML
    void onBackButtonPressed(ActionEvent event){
        eventBus.post(new ShowMainMenuViewEvent());
    }
}
