package it.polimi.ingsw.view.gui.scene;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Locale;

public class VictorySceneManager {

    private Stage stage;

    @FXML
    private ImageView victoryGif;

    @FXML
    private Text winner;

    public void setWinner(String s){
        winner.setText(s.toUpperCase());
    }

    public void showVictoryScene() {
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

}
