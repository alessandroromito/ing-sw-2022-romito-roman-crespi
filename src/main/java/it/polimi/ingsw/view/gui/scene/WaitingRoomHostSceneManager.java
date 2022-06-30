package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Lobby scene manager. Shows the players waiting to start the game.
 */
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

    /**
     * Set the nicknames of the players waiting to join the game.
     * @param playersNickname list of string of nickname of players.
     */
    public void setPlayersNickname(List<String> playersNickname){
        this.playersNickname = playersNickname;
    }

    /**
     * Set the maximum number of player contained in the lobby.
     * @param maxPlayers maximum number of player.
     */
    public void setNumMaxPlayers(int maxPlayers){
        this.numMaxPlayers = maxPlayers;
    }

    /**
     * Update the values of the lobby scene.
     */
    public void updateValues(){
        initialize();
    }
}
