package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.SceneManager;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GenericMessageSceneManager implements SceneManagerInterface {
    private final Stage stage;

    //cose di fxml

    //metodi aggiunte fxml

    public GenericMessageSceneManager() {
        stage = new Stage();
        stage.initOwner(SceneManager.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    public void setErrorTitle(String title) {
        //setText sul titolo
    }

    public void setErrorMessage(String message) {
        //setText sul message
    }

    public void showMessage() {
        stage.showAndWait();
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
