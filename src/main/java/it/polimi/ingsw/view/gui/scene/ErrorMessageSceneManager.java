package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorMessageSceneManager implements SceneManagerInterface {

    private final Stage stage;

    @FXML
    private Label titleText;

    @FXML
    private Button okButton;

    @FXML
    private Label messageText;

    public ErrorMessageSceneManager() {
        stage = new Stage();
        stage.initOwner(GraphicController.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    public void setErrorTitle(String title) {
        titleText.setText(title);
    }

    public void setErrorMessage(String message) {
        messageText.setText(message);
    }

    public void showMessage() {
        stage.showAndWait();
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    @FXML
    void onOkButtonClick(ActionEvent event) {
        stage.close();
    }
}
