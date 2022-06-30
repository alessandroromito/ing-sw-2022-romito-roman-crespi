package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

/**
 * This class represents the manager of the game mode scene. It allows to the player to choose how many user to play with.
 */
public class GameModeSelectSceneManager extends ViewObservable implements SceneManagerInterface {

    private final ObservableList<String> choices = FXCollections.observableArrayList("Normale","Esperta");

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> gameMode;


    /**
     * Handles the click of the ok button.
     * @param event mouse event.
     */
    @FXML
    private void onButtonClick(Event event) {
        confirmButton.setDisable(true);
        String gameMode = getSelection();
        System.out.println(gameMode);
        new Thread( () -> notifyObserver( obs -> obs.onUpdateGameMode(gameMode))).start();
        //anche se si sceglie esperto viene inviato messaggio di ritorno con modalità normale
    }

    /**
     * @return the selection of the gameMode choiceBox
     */
    public String getSelection(){
        return gameMode.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {
        gameMode.setItems(choices);
        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onButtonClick);
    }
}
