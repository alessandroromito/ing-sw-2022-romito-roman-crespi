package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.GraphicController;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Manager of the login scene.
 */
public class LoginSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private Button connectButton;

    @FXML
    private TextField nicknameField;

    @FXML
    private ImageView loading;

    /**
     * Handles the click of the button sending a message with the nickname requested to the server.
     * @param event mouse event.
     */
    @FXML
    void onButtonClick(ActionEvent event) {
        String nickname = nicknameField.getText();
        boolean isNicknameValid;
        isNicknameValid = !nickname.equals("");

        nicknameField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), !isNicknameValid);

        if(isNicknameValid) {
            loading.setVisible(true);
            GraphicController.nickname = nickname;
            connectButton.setDisable(true);
            new Thread(() -> notifyObserver(obs -> obs.onUpdateNickname(nickname))).start();
        }
    }
}
