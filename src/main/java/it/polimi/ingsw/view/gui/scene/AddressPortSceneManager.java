package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.cli.Validator;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;

/**
 * Manager of the scene where the player choose ip address and port of the server.
 */
public class AddressPortSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private Parent parent;

    @FXML
    private Button connectButton;

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    /**
     * Handle the click on the connect button.
     * @param event mouse click event.
     */
    @FXML
    void onButtonClick(ActionEvent event) {
        String address = addressField.getText();
        String port = portField.getText();

        boolean isAddressValid = Validator.validateIpAddress(address);
        boolean isPortValid = Validator.validatePort(port);

        addressField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), !isAddressValid);
        portField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), !isPortValid);

        if(isAddressValid && isPortValid) {
            connectButton.setDisable(true);
            HashMap<String, String> server = new HashMap<>();
            server.put("address", address);
            server.put("port", port);
            new Thread(() -> notifyObserver(obs -> obs.onUpdateServerDetails(server))).start();
        }

    }

}