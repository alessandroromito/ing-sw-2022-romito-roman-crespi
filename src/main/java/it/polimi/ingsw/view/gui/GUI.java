package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.gui.scene.StartMenuSceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;

public class GUI extends Application {


    @Override
    public void start(Stage stage) {
        try {
            GraphicController graphicController = new GraphicController();
            ClientController clientController = new ClientController(graphicController);
            graphicController.addObserver(clientController);

            InputStream inputStream = GUI.class.getClassLoader().getResourceAsStream("images/icon.png");
            if(inputStream!=null) stage.getIcons().add(new Image(inputStream));

            stage.setScene(new Scene(new Pane()));
            //stage.setMaximized(true);
            //stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setTitle("Eryantis Game");

            //GraphicController.setLayout(stage.getScene(), "fxml/playQuit_scene.fxml");

            StartMenuSceneManager manager = GraphicController.setLayout(stage.getScene(), "fxml/playQuit_scene.fxml");
            if (manager != null) {
                manager.addObserver(clientController);
            }
            else throw new NullPointerException();

            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
