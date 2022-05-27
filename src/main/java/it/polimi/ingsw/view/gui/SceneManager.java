package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.sceneManager.SceneManagerInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SceneManager extends ViewObservable {
    private static Scene activeScene;
    private static SceneManagerInterface activeManager;


    public static Scene getActiveScene() {
        return activeScene;
    }

    public static SceneManagerInterface getActiveManager() {
        return activeManager;
    }

    public static <T> T paneTransition(List<ViewObserver> observerList, String fxml) {
        return paneTransition(observerList, activeScene, fxml);
    }

    public static <T> T paneTransition(List<ViewObserver> observerList, Scene scene, String fxml) {
        T manager = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxml));
            Parent parent = fxmlLoader.load();
            manager = fxmlLoader.getController();
            ((ViewObservable) manager).addAllObservers(observerList);

            activeManager = (SceneManagerInterface) manager;
            activeScene = scene;
            activeScene.setRoot(parent);
        } catch(IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
        }
        return manager;
    }

    public static void paneTransition(SceneManagerInterface sceneManager, String fxml) {
        paneTransition(sceneManager, activeScene, fxml);
    }

    public static void paneTransition(SceneManagerInterface sceneManager, Scene scene, String fxml) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxml));
            fxmlLoader.setController(sceneManager);
            activeManager = sceneManager;
            Parent parent = fxmlLoader.load();
            activeScene = scene;
            activeScene.setRoot(parent);
        }catch(IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
        }
    }
}
