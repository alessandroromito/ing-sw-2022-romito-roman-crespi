package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private Button connectButton;

    @FXML
    private TextField nicknameField;

    @FXML
    void onButtonClick(ActionEvent event) {
        connectButton.setDisable(true);
        String nickname = nicknameField.getText();
        new Thread( () -> notifyObserver(obs -> obs.onUpdateNickname(nickname)) ).start();
    }
}
