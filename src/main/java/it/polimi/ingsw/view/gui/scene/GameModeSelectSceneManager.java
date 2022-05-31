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
    private Button selectButton;

    @FXML
    private ChoiceBox<String> gameMode;


    @FXML
    void onButtonClick(ActionEvent event) {
        //send the message

    }
//ritorna true se modalità esperto
    public boolean getSelection(){
        return (gameMode.getSelectionModel().getSelectedItem() == "Esperto");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameMode.setItems(choices);
    }
}
