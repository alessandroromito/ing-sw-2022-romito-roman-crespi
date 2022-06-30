package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.GraphicController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Manager of the first scene of the gui.
 */
public class StartMenuSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private AnchorPane parentPane;
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;

    /**
     * Handles the click on the play button.
     * @param event mouse event.
     */
    @FXML
    private void onPlayButtonClick(Event event) {
        GraphicController.paneTransition(observers, event, "addressPort_scene.fxml");
    }

    /**
     * Handles the click of the quit button.
     * @param actionEvent mouse event.
     */
    @FXML
    public void onQuitButtonClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}
