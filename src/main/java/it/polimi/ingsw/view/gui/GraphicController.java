package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.GameModeSelectSceneManager;
import it.polimi.ingsw.view.gui.scene.PlayersNumberSceneManager;
import it.polimi.ingsw.view.gui.scene.WaitingRoomHostSceneManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class GraphicController extends ViewObservable implements View {

    public static <T> T setLayout(Scene scene, String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getClassLoader().getResource(path));

        Pane pane;
        try {
            pane = fxmlLoader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
            return null;
        }
        return fxmlLoader.getController();
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
        Platform.runLater(() -> SceneManager.paneTransition(observers, "login_scene.fxml"));
    }

    @Override
    public void askPlayersNumber() {
        PlayersNumberSceneManager playersNumberSceneManager = new PlayersNumberSceneManager();
        playersNumberSceneManager.addAllObservers(observers);
        System.out.println("Finestra playerNumber");
        Platform.runLater(() -> SceneManager.paneTransitionNoController(playersNumberSceneManager, "playersNumber_scene.fxml"));
    }

    @Override
    public void askGameMode() {
        GameModeSelectSceneManager gameModeSelectSceneManager = new GameModeSelectSceneManager();
        gameModeSelectSceneManager.addAllObservers(observers);
        Platform.runLater(() -> SceneManager.paneTransitionNoController(gameModeSelectSceneManager, "gameModeSelect_scene.fxml"));
    }

    @Override
    public void showLobby(List<String> playersNickname, int numPlayers) {
        WaitingRoomHostSceneManager waitingRoomHostSceneManager;
        try{
            waitingRoomHostSceneManager = (WaitingRoomHostSceneManager) SceneManager.getActiveManager();
            waitingRoomHostSceneManager.setPlayersNickname(playersNickname);
            waitingRoomHostSceneManager.setNumMaxPlayers(numPlayers);
            Platform.runLater(waitingRoomHostSceneManager::updateValues);
        } catch (ClassCastException e){
            waitingRoomHostSceneManager = new WaitingRoomHostSceneManager();
            waitingRoomHostSceneManager.addAllObservers(observers);
            waitingRoomHostSceneManager.setPlayersNickname(playersNickname);
            waitingRoomHostSceneManager.setNumMaxPlayers(numPlayers);
            WaitingRoomHostSceneManager lobbySceneManagerToExecute = waitingRoomHostSceneManager;
            Platform.runLater( () -> SceneManager.paneTransitionNoController(lobbySceneManagerToExecute, "waitingRoomHost_scene.fxml"));
        }
    }

    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected, String text) {

    }

    @Override
    public void showErrorMessage(String error) {

    }

    @Override
    public void showGameScenario(GameSerialized gameSerialized) {

    }

    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland) {

    }

    @Override
    public void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {

    }

    @Override
    public void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {

    }

    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {

    }

    @Override
    public void askToMoveMotherNature(int maxSteps) {

    }

    @Override
    public void askToChooseACloud(List<Cloud> cloudList) {

    }

    @Override
    public void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful) {

    }

    @Override
    public void showVictoryMessage(String winner) {

    }
}
