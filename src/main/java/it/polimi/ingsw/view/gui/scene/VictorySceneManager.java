package it.polimi.ingsw.view.gui.scene;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Locale;

public class VictorySceneManager {

    @FXML
    private ImageView victoryGif;

    @FXML
    private Text winner;

    public void setWinner(String s){
        winner.setText(s.toUpperCase());
    }

}
