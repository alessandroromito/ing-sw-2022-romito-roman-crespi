package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.GameModeSelectSceneManager;
import it.polimi.ingsw.view.gui.scene.MapSceneManager;
import it.polimi.ingsw.view.gui.scene.PlayersNumberSceneManager;
import it.polimi.ingsw.view.gui.scene.WaitingRoomHostSceneManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
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
        Platform.runLater(() -> SceneManager.paneTransition(playersNumberSceneManager, "playersNumber_scene.fxml"));
    }

    @Override
    public void askGameMode() {
        GameModeSelectSceneManager gameModeSelectSceneManager = new GameModeSelectSceneManager();
        gameModeSelectSceneManager.addAllObservers(observers);
        Platform.runLater(() -> SceneManager.paneTransition(gameModeSelectSceneManager, "gameModeSelect_scene.fxml"));
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
        Platform.runLater( () -> {
            SceneManager.showGenericMessage("FINE DELLA PARTITA", "Il gicatore " + nicknameDisconnected + " si è disconnesso.");
            SceneManager.paneTransition(observers, "scene_menu.fxml");
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Platform.runLater( () -> {
            SceneManager.showErrorMessage("ERROR", errorMessage);
            SceneManager.paneTransition(observers, "scene_menu.fxml");
        } );
    }

    @Override
    public void showGameScenario(GameSerialized gameSerialized) {
        System.out.println("Starting game scenario");
        MapSceneManager mapSceneManager = getMapSceneManager();
        Platform.runLater( () -> mapSceneManager.updateValues(gameSerialized) );
    }

    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland) {
        MapSceneManager mapSceneManager = getMapSceneManager();
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
            getMapSceneManager().initializeCharacterCards(finalCharacterNumbers);
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
            SceneManager.showGenericMessage("INFO", genericMessage);
            //SceneManager.paneTransition(observers, "scene_menu.fxml");
        } );
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        Platform.runLater( () -> {
            getMapSceneManager().enableAssistant(assistantCards);
        });
    }

    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {

    }

    @Override
    public void askToMoveMotherNature(int maxSteps) {
        MapSceneManager mapSceneManager = getMapSceneManager();
        Platform.runLater( () -> mapSceneManager.moveMotherNature(maxSteps) );
    }

    @Override
    public void askToChooseACloud(ArrayList<Cloud> cloudList) {
        Platform.runLater( () -> {
            getMapSceneManager().addStudentsToCloud(cloudList.get(0),1);
            getMapSceneManager().addStudentsToCloud(cloudList.get(1),2);}
        );
    }

    @Override
    public void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful) {

    }

    @Override
    public void showVictoryMessage(String winner) {
        Platform.runLater( () -> {
            SceneManager.showWinner(winner);
            SceneManager.paneTransition(observers, "scene_menu.fxml");
        });
    }

    private MapSceneManager getMapSceneManager(){
        MapSceneManager mapSceneManager;
        try{
            mapSceneManager = (MapSceneManager) SceneManager.getActiveManager();
        } catch(ClassCastException e){
            mapSceneManager = new MapSceneManager();
            mapSceneManager.addAllObservers(observers);
            MapSceneManager finalMapSceneManager = mapSceneManager;
            Platform.runLater( () -> SceneManager.paneTransitionNoController(finalMapSceneManager, "mapExpert_scene.fxml") );
        }
        return mapSceneManager;
    }
}
