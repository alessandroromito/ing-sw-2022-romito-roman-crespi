package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GenericMessageSceneManager implements SceneManagerInterface {
    private final Stage stage;

    @FXML
    private Label titleText;

    @FXML
    private Button okButton;

    @FXML
    private Label messageText;

    //metodi aggiunte fxml

    public GenericMessageSceneManager() {
        stage = new Stage();
        stage.initOwner(SceneManager.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    public void setGenericTitle(String title) {
        titleText.setText(title);
    }

    public void setGenericMessage(String message) {
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
