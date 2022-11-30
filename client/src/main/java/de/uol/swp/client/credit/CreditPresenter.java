package de.uol.swp.client.credit;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.common.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreditPresenter extends AbstractPresenter {
    /**
     * Manages the rulebook window
     *
     * @author Tommy Dang
     * @see de.uol.swp.client.AbstractPresenter
     * @since 2022-11-30
     *
     */

    public static final String FXML = "/fxml/CreditView.fxml";

    private User loggedInUser;

    /**
     * Default Constructor
     *
     * @since 2022-11-30
     */
    public CreditPresenter() {
    }

    @FXML
    void onBackButtonPressed(ActionEvent event){
        eventBus.post(new ShowMainMenuViewEvent());
    }
}
