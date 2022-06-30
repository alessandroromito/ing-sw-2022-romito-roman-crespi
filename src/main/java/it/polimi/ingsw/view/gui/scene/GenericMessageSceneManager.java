package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Manager of the generic message scene.
 */
public class GenericMessageSceneManager implements SceneManagerInterface {
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
    public GenericMessageSceneManager() {
        stage = new Stage();
        stage.initOwner(GraphicController.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    /**
     * Set the title of the generic message.
     * @param title title to be set.
     */
    public void setGenericTitle(String title) {
        titleText.setText(title);
    }

    /**
     * Set the text message of the generic message.
     * @param message text message to be set.
     */
    public void setGenericMessage(String message) {
        messageText.setText(message);
    }

    /**
     * Show the message and wait.
     */
    public void showMessage() {
        stage.showAndWait();
    }

    /**
     * Set the scene of generic message.
     * @param scene scene to be set.
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Close the generic message scene.
     * @param event mouse event.
     */
    @FXML
    void onOkButtonClick(ActionEvent event) {
        stage.close();
    }
}
