package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class GameModeSelectSceneManager extends ViewObservable implements SceneManagerInterface {

    private final ObservableList<String> choices = FXCollections.observableArrayList("Normale","Esperto");

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> gameMode;


    @FXML
    private void onButtonClick(Event event) {
        confirmButton.setDisable(true);
        String gameMode = getSelection();
        new Thread( () -> notifyObserver( obs -> obs.onUpdateGameMode(gameMode))).start();
        //anche se si sceglie esperto viene inviato messaggio di ritorno con modalità normale
    }

    public String getSelection(){
        return gameMode.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {
        gameMode.setItems(choices);
        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onButtonClick);
    }
}
