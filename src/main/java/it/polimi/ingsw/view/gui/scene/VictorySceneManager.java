package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
     * Default constructor.
     */
    public VictorySceneManager(){
        stage = new Stage();
        stage.initOwner(GraphicController.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

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
