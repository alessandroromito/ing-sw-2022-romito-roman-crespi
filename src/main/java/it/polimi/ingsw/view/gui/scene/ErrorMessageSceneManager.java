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

/**
 * Manager of the error message scene.
 */
public class ErrorMessageSceneManager implements SceneManagerInterface {

    private final Stage stage;

    @FXML
    private Label titleText;

    @FXML
    private Button okButton;

    @FXML
    private Label messageText;

    /**
     * Default constructor.
     */
    public ErrorMessageSceneManager() {
        stage = new Stage();
        stage.initOwner(GraphicController.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    /**
     * Set the error title.
     * @param title title to be set.
     */
    public void setErrorTitle(String title) {
        titleText.setText(title);
    }

    /**
     * Set the message of the error.
     * @param message text message.
     */
    public void setErrorMessage(String message) {
        messageText.setText(message);
    }

    /**
     * Method that shows the message and wait.
     */
    public void showMessage() {
        stage.showAndWait();
    }

    /**
     * Method that set the scene.
     * @param scene scene to be set.
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Handle the click of the ok button closing the stage.
     * @param event mouse event.
     */
    @FXML
    void onOkButtonClick(ActionEvent event) {
        stage.close();
    }
}
