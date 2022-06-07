package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameModeSelectSceneManager extends ViewObservable implements SceneManagerInterface, Initializable {
    private ObservableList<String> choices = FXCollections.observableArrayList("Normale","Esperto");

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> gameMode;


    @FXML
    void onButtonClick(ActionEvent event) {
        confirmButton.setDisable(true);
        String gameMode = getSelection();
        new Thread( () -> notifyObserver( obs -> obs.onUpdateGameMode(gameMode)));
    }

    public String getSelection(){
        return gameMode.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameMode.setItems(choices);
    }
}
