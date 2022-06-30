package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Manager of the player number choosing scene.
 */
public class PlayersNumberSceneManager extends ViewObservable implements SceneManagerInterface {

    private final ObservableList<Integer> choices = FXCollections.observableArrayList(2,3);

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<Integer> nplayer;

    @FXML
    private ImageView loading;


    /**
     * Handles the ok click button.
     * @param event mouse event.
     */
    @FXML
    void onButtonClick(Event event) {
        confirmButton.setDisable(true);
        int playerNumberFinal = getSelection();
        loading.setVisible(true);
        new Thread( () -> notifyObserver( obs -> obs.onUpdatePlayersNumber(playerNumberFinal))).start();
    }

    /**
     * @return the selection of the choiceBox choices.
     */
    public Integer getSelection(){
        return nplayer.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {
        nplayer.setItems(choices);
        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onButtonClick);
    }
}
