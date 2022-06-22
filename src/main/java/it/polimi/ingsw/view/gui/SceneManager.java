package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.scene.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

            Parent parent = fxmlLoader.load();

            //System.out.println((String) fxmlLoader.getController());
            fxmlLoader.setController(sceneManager);
            activeManager = sceneManager;

            activeScene = scene;
            activeScene.setRoot(parent);
        }catch(IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void paneTransitionNoController(SceneManagerInterface sceneManager, String fxml) {
        paneTransitionNoController(sceneManager, activeScene, fxml);
    }

    public static void paneTransitionNoController(SceneManagerInterface sceneManager, Scene scene, String fxml) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxml));
            //fxmlLoader.setController(sceneManager);

            activeManager = sceneManager;
            Parent parent = fxmlLoader.load();
            activeScene = scene;
            activeScene.setRoot(parent);
        }catch(IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public static <T> T paneTransition(List<ViewObserver> observerList, Event event, String fxml){
        Scene scene = ((Node) event.getSource()).getScene();
        return paneTransition(observerList, scene, fxml);
    }

    public static void showErrorMessage(String errorTitle, String errorMessage) {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/messageAlert_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //nel caso in cui non avessimo il controllo da parte di fxml manager
        //ErrorMessageManager errorMessageManager = fxmlLoader.getController();
        //altrimenti
        ErrorMessageSceneManager errorMessageSceneManager = new ErrorMessageSceneManager();
        Scene errorMessageScene = new Scene(parent);
        errorMessageSceneManager.setScene(errorMessageScene);
        errorMessageSceneManager.setErrorTitle(errorTitle);
        errorMessageSceneManager.setErrorMessage(errorMessage);
        errorMessageSceneManager.showMessage();
    }

    public static void showGenericMessage(String genericMessageTitle, String genericMessageText) {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/genericMessage_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //nel caso in cui non avessimo il controllo da parte di fxml manager
        GenericMessageSceneManager genericMessageSceneManager = fxmlLoader.getController();
        //altrimenti
        //GenericMessageSceneManager genericMessageSceneManager = new GenericMessageSceneManager();
        Scene genericMessageScene = new Scene(parent);
        genericMessageSceneManager.setScene(genericMessageScene);
        genericMessageSceneManager.setGenericTitle(genericMessageTitle);
        genericMessageSceneManager.setGenericMessage(genericMessageText);
        genericMessageSceneManager.showMessage();
    }

    public static ScoreboardSceneManager showScoreboards() {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/scoreboard_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        ScoreboardSceneManager scoreboardSceneManager = fxmlLoader.getController();
        Scene scoreboardScene = new Scene(parent);
        scoreboardSceneManager.setScene(scoreboardScene);
        scoreboardSceneManager.showScoreboards();
        return scoreboardSceneManager;
    }

    public static void showWinner(String winner) {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/victory_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        VictorySceneManager victorySceneManager = fxmlLoader.getController();
        Scene victoryScene = new Scene(parent);
        victorySceneManager.setScene(victoryScene);
        victorySceneManager.setWinner(winner);
        victorySceneManager.showVictoryScene();
    }
}
