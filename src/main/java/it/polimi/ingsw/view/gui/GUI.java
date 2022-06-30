package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.gui.scene.StartMenuSceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * This class represents the JavaFx application of the GUI.
 */
public class GUI extends Application {


    /**
     * Start the application.
     * @param stage default parameter for javafx application.
     */
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
            stage.setTitle("Eriantys Game");


            FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getClassLoader().getResource("fxml/playQuit_scene.fxml"));
            Pane pane;
            try {
                pane = fxmlLoader.load();
                stage.getScene().setRoot(pane);
            } catch (IOException e) {
                Logger.getLogger("client").severe(e.getMessage());
                return;
            }
            StartMenuSceneManager manager = fxmlLoader.getController();
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

    /**
     * Stop the application.
     */
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
