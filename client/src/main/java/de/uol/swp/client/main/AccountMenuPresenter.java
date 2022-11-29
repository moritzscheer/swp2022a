package de.uol.swp.client.main;

import de.uol.swp.client.AbstractPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountMenuPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/AccountView.fxml";

    private static final Logger LOG = LogManager.getLogger(AccountMenuPresenter.class);

    @FXML
    private Button DeleteAccountButton, DeletePasswordButton, PasswordResetButton, BackButton;

    /**
     * Default Constructor
     * @since 2022-11-25
     *
     */
    public AccountMenuPresenter() {
        // needed for javafx
    }

    @FXML
    private void onDeleteAccountButtonPressed(ActionEvent event) {

    }
    @FXML
    private void onPasswordResetButtonPressed(ActionEvent event) {

    }
    @FXML
    private void onBackButtonPressed(ActionEvent event) {

    }
}
