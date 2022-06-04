package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StartMenuSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private AnchorPane parentPane;
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;

    @FXML
    public void initialize() {
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayButtonClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    private void onPlayButtonClick(Event event) {
        SceneManager.paneTransition(observers, event, "connect_scene.fxml");
    }

}
