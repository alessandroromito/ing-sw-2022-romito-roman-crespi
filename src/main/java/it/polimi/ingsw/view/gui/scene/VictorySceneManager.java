package it.polimi.ingsw.view.gui.scene;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Manager for the victory scene. Shows the winner.
 */
public class VictorySceneManager {

    private Stage stage;

    @FXML
    private ImageView victoryGif;

    @FXML
    private Text winner;

    /**
     * Set the nickname of the winner.
     * @param s nickname of the winner.
     */
    public void setWinner(String s){
        winner.setText(s.toUpperCase());
    }

    /**
     * Show and wait the scene.
     */
    public void showVictoryScene() {
        stage.showAndWait();
    }

    /**
     * Set the victory scene.
     * @param scene to be set.
     */
    public void setScene(Scene scene){
        stage.setScene(scene);
    }

}
