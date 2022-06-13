package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

public class WaitingRoomHostSceneManager extends ViewObservable implements SceneManagerInterface {

    private List<String> playersNickname;
    private int numMaxPlayers;

    @FXML
    private Label information;

    @FXML
    private Label information1;

    @FXML
    private Label connectedPlayersNumber;

    @FXML
    private ListView<String> playersList;

    @FXML
    private Button startButton;

    @FXML
    public void initialize(){
        connectedPlayersNumber.setText(playersNickname.size() + "/" + numMaxPlayers);
        for( int i = 0; i < playersNickname.size()-1 ; i++ ){
            playersList.getItems().add(playersNickname.get(i));
        }
    }

    public void setPlayersNickname(List<String> playersNickname){
        this.playersNickname = playersNickname;
    }

    public void setNumMaxPlayers(int maxPlayers){
        this.numMaxPlayers = maxPlayers;
    }

    public void updateValues(){
        initialize();
    }

    @FXML
    void startGame(ActionEvent event) {
        //da eliminare... il gameController farà partire il game... chiedere a Teo
    }

}
