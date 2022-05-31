package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private Button ConnectButton;

    @FXML
    private TextField NicknameField;

    @FXML
    void onButtonClick(ActionEvent event) {
        //send the message

    }

}
