package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class StartMenuSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private AnchorPane parentPane;
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;

    @FXML
    private void onPlayButtonClick(Event event) {
        SceneManager.paneTransition(observers, event, "addressPort_scene.fxml");
    }

    @FXML
    public void onQuitButtonClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}
