package it.polimi.ingsw.view.gui.graphicController;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.logging.Logger;

public class GraphicController extends ViewObservable implements View {

    private static GraphicController graphicController = null;

    public static <T> T setLayout(Scene scene, String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getClassLoader().getResource(path));

        Pane pane;
        try {
            pane = fxmlLoader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Logger.getLogger("adrenaline_client").severe(e.getMessage());
            return null;
        }

        return fxmlLoader.getController();
    }
}
