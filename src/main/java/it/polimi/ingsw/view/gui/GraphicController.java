package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GraphicController extends ViewObservable implements View {

    private static Scene activeScene;
    private static SceneManagerInterface activeManager;
    private boolean gameScenarioEnabled = false;
    MapSceneManager mapSceneManager;


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
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/" + fxml));
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
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/" + fxml));

            //Parent parent = fxmlLoader.load();

            //System.out.println((String) fxmlLoader.getController());
            fxmlLoader.setController(sceneManager);
            activeManager = sceneManager;
            Parent parent = fxmlLoader.load();
            activeScene = scene;
            activeScene.setRoot(parent);
        }catch(IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
            e.printStackTrace();
        }
    }


    public synchronized void getAndPaneTransitionGameScenario() {
        if(!gameScenarioEnabled)
        {
            System.out.println("Game scenario in creation");
            MapSceneManager mapSceneManager;// = new MapSceneManager();
            //mapSceneManager.addAllObservers(observers);
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/mapExpert_scene.fxml"));
                Parent parent = fxmlLoader.load();

                mapSceneManager = fxmlLoader.getController();
                mapSceneManager.addAllObservers(observers);
                //fxmlLoader.setController(mapSceneManager);
                System.out.println(fxmlLoader.getController().toString());
                activeManager = mapSceneManager;
                //Parent parent = fxmlLoader.load();
                activeScene.setRoot(parent);
                gameScenarioEnabled = true;
                this.mapSceneManager = mapSceneManager;
            }catch(IOException e) {
                Logger.getLogger("client").severe(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void paneTransitionNoController(SceneManagerInterface sceneManager, String fxml) {
        paneTransitionNoController(sceneManager, activeScene, fxml);
    }

    public static void paneTransitionNoController(SceneManagerInterface sceneManager, Scene scene, String fxml) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/" + fxml));
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
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/messageAlert_scene.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/genericMessage_scene.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/scoreboard_scene.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/victory_scene.fxml"));
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

    /*public static <T> T setLayout(Scene scene, String path, ClientController clientController) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getClassLoader().getResource(path));

        Pane pane;
        try {
            pane = fxmlLoader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
            return null;
        }
        StartMenuSceneManager manager = new StartMenuSceneManager();
        manager.addObserver(clientController);
        return fxmlLoader.getController();
    }
     */

    @Override
    public void askPlayerNickname() {
        Platform.runLater(() -> paneTransition(observers, "login_scene.fxml"));
    }

    @Override
    public void askPlayersNumber() {
        PlayersNumberSceneManager playersNumberSceneManager = new PlayersNumberSceneManager();
        playersNumberSceneManager.addAllObservers(observers);
        Platform.runLater(() -> paneTransition(playersNumberSceneManager, "playersNumber_scene.fxml"));
    }

    @Override
    public void askGameMode() {
        GameModeSelectSceneManager gameModeSelectSceneManager = new GameModeSelectSceneManager();
        gameModeSelectSceneManager.addAllObservers(observers);
        Platform.runLater(() -> paneTransition(gameModeSelectSceneManager, "gameModeSelect_scene.fxml"));
    }

    @Override
    public void showLobby(List<String> playersNickname, int numPlayers) {
        WaitingRoomHostSceneManager waitingRoomHostSceneManager;
        try{
            waitingRoomHostSceneManager = (WaitingRoomHostSceneManager) getActiveManager();
            waitingRoomHostSceneManager.setPlayersNickname(playersNickname);
            waitingRoomHostSceneManager.setNumMaxPlayers(numPlayers);
            Platform.runLater(waitingRoomHostSceneManager::updateValues);
        } catch (ClassCastException e){
            waitingRoomHostSceneManager = new WaitingRoomHostSceneManager();
            waitingRoomHostSceneManager.addAllObservers(observers);
            waitingRoomHostSceneManager.setPlayersNickname(playersNickname);
            waitingRoomHostSceneManager.setNumMaxPlayers(numPlayers);
            WaitingRoomHostSceneManager lobbySceneManagerToExecute = waitingRoomHostSceneManager;
            Platform.runLater( () -> paneTransitionNoController(lobbySceneManagerToExecute, "waitingRoomHost_scene.fxml"));
        }
    }

    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected, String text) {
        Platform.runLater( () -> {
            showGenericMessage("FINE DELLA PARTITA", "Il gicatore " + nicknameDisconnected + " si è disconnesso.");
            paneTransition(observers, "scene_menu.fxml");
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Platform.runLater( () -> {
            showErrorMessage("ERROR", errorMessage);
            paneTransition(observers, "scene_menu.fxml");
        } );
    }

    @Override
    public void showGameScenario(GameSerialized gameSerialized) {
        System.out.println("Starting game scenario");
        getAndPaneTransitionGameScenario();
        Platform.runLater( () -> mapSceneManager.updateValues(gameSerialized) );
    }

    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland) {
        //max 2 alla volta
        Integer minValue = 12;
        for (Integer integer : unifiedIsland)
            if (integer < minValue) minValue = integer;
        if(minValue == 1 && (unifiedIsland.get(0) == 11 || unifiedIsland.get(1) == 11))
            minValue = 11;
        Integer finalMinValue = minValue;
        Platform.runLater( () -> mapSceneManager.build(finalMinValue) );
    }

    @Override
    public void askCharacterCard(List<CharacterCard> characterCards) {
        int[] finalCharacterNumbers = new int[3];
        for(int i = 0 ; i < characterCards.size(); i++)
        {
            finalCharacterNumbers[i] = characterCards.get(i).getID() - 213;
        }

        Platform.runLater( () -> {
            mapSceneManager.initializeCharacterCards(finalCharacterNumbers);
        });
    }

    @Override
    public void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {

    }

    @Override
    public void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater( () -> {
            //SceneManager.showGenericMessage("INFO", genericMessage);
            //SceneManager.paneTransition(observers, "scene_menu.fxml");
        } );
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        System.out.println("entriamo in askassistant");
        Platform.runLater( () -> {
            mapSceneManager.enableAssistant(assistantCards);
            showGenericMessage("Scelta della carta", "Scegli la carta assistente!");
        });
    }

    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {

    }

    @Override
    public void askToMoveMotherNature(int maxSteps) {
        Platform.runLater( () -> mapSceneManager.moveMotherNature(maxSteps) );
    }

    @Override
    public void askToChooseACloud(ArrayList<Cloud> cloudList) {
        Platform.runLater( () -> {
            mapSceneManager.addStudentsToCloud(cloudList.get(0),1);
            mapSceneManager.addStudentsToCloud(cloudList.get(1),2);}
        );
    }

    @Override
    public void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful) {

    }

    @Override
    public void showVictoryMessage(String winner) {
        Platform.runLater( () -> {
            showWinner(winner);
            paneTransition(observers, "scene_menu.fxml");
        });
    }
}
