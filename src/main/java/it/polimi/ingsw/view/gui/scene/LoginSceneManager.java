package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginSceneManager implements SceneManagerInterface {

    @FXML
    private Button ConnectButton;

    @FXML
    private TextField NicknameField;

    @FXML
    private TextField PortField;

    @FXML
    void tryConnection(ActionEvent event) {

    }

}
