package it.polimi.ingsw.view.gui.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class WaitingRoomHostSceneManager {

    @FXML
    private Label information;

    @FXML
    private ListView<String> playersList = new ListView<>();

    @FXML
    private Button startBtn;

    public void addPlayer(String s){
        playersList.getItems().add(s);
    }

    @FXML
    void startGame(ActionEvent event) {

    }

}
