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

public class PlayersNumberSceneManager extends ViewObservable implements SceneManagerInterface,Initializable {

    private ObservableList<Integer> choices = FXCollections.observableArrayList(2,3);

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<Integer> n_player;


    @FXML
    void onButtonClick(ActionEvent event) {
        confirmButton.setDisable(true);
        int playerNumberFinal = getSelection();
        new Thread( () -> notifyObserver( obs -> obs.onUpdatePlayersNumber(playerNumberFinal)));
    }

    public Integer getSelection(){
        return n_player.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        n_player.setItems(choices);
    }
}
