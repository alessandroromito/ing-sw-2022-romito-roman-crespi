package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.enumerations.PawnColors;
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
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class that control the GUI and implement View.
 * This class represents the GUI (Graphic User Interface).
 */
public class GraphicController extends ViewObservable implements View {

    private static Scene activeScene;
    private static SceneManagerInterface activeManager;
    private boolean gameScenarioEnabled = false;
    static public String nickname;
    static public String nicknameOpponent1 = null;
    static public String nicknameOpponent2 = null;
    MapSceneManager mapSceneManager;
    private static ScoreboardSceneManager scoreboardSceneManager = null;

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static SceneManagerInterface getActiveManager() {
        return activeManager;
    }

    public static <T> T paneTransition(List<ViewObserver> observerList, String fxml) {
        return paneTransition(observerList, activeScene, fxml);
    }

    /**
     * Make a scene transition.
     * @param observerList list of observer to be added to the new scene.
     * @param scene new scene.
     * @param fxml path for resource fxml.
     * @param <T>
     * @return manager(controller) of the fxml file.
     */
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

    /**
     * Make a scene transition.
     * @param sceneManager sceneManager for the new scene.
     * @param fxml path for resource fxml.
     */
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


    /**
     * Customized method that switches from login part to game part.
     */
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
                mapSceneManager.setGraphicController(this);
                mapSceneManager.fullscreen();
            }catch(IOException e) {
                Logger.getLogger("client").severe(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Make a scene transition without a fxml controller setted.
     * @param sceneManager sceneManager for the new scene.
     * @param fxml path resource for fxml file.
     */
    public static void paneTransitionNoController(SceneManagerInterface sceneManager, String fxml) {
        paneTransitionNoController(sceneManager, activeScene, fxml);
    }

    /**
     * Make a scene transition without a fxml controller setted.
     * @param sceneManager sceneManager for the new scene.
     * @param scene new scene.
     * @param fxml path resource for fxml file.
     */
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

    /**
     * Make a scene transition.
     * @param observerList list of observer to be added to the new scene.
     * @param event event happened to switch the scene.
     * @param fxml path resource for fxml file.
     * @param <T>
     * @return the manager(controller) of the scene.
     */
    public static <T> T paneTransition(List<ViewObserver> observerList, Event event, String fxml){
        Scene scene = ((Node) event.getSource()).getScene();
        return paneTransition(observerList, scene, fxml);
    }

    /**
     * Shows an error message.
     * @param errorTitle title.
     * @param errorMessage text message.
     */
    public static void showErrorMessage(String errorTitle, String errorMessage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/errorMessage_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ErrorMessageSceneManager errorMessageSceneManager = fxmlLoader.getController();
        Scene errorMessageScene = new Scene(parent);
        errorMessageSceneManager.setScene(errorMessageScene);
        errorMessageSceneManager.setErrorTitle(errorTitle);
        errorMessageSceneManager.setErrorMessage(errorMessage);
        errorMessageSceneManager.showMessage();
    }

    /**
     * Shows a generic message.
     * @param genericMessageTitle title.
     * @param genericMessageText text message.
     */
    public static void showGenericMessage(String genericMessageTitle, String genericMessageText) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getResource("/fxml/genericMessage_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        GenericMessageSceneManager genericMessageSceneManager = fxmlLoader.getController();
        Scene genericMessageScene = new Scene(parent);
        genericMessageSceneManager.setScene(genericMessageScene);
        genericMessageSceneManager.setGenericTitle(genericMessageTitle);
        genericMessageSceneManager.setGenericMessage(genericMessageText);
        genericMessageSceneManager.showMessage();
    }

    /**
     * Initialize the scoreboard manager to be ready for call.
     */
    public synchronized void initializeScoreboard(){
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicController.class.getClassLoader().getResource("fxml/scoreboard_scene.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        scoreboardSceneManager = fxmlLoader.getController();
        scoreboardSceneManager.addAllObservers(observers);
        Scene scoreboardScene = new Scene(parent);
        scoreboardSceneManager.setScene(scoreboardScene);
    }

    /**
     * Shows the scoreboard.
     * @param snapshot snapshot of the match in progress.
     * @return the manager for scoreboard.
     */
    public ScoreboardSceneManager showScoreboards(Image snapshot) {
        if (scoreboardSceneManager == null) initializeScoreboard();
        else{ scoreboardSceneManager.showScoreboards();
        scoreboardSceneManager.setMap(snapshot);}
        return scoreboardSceneManager;
    }

    /**
     * Shows the winner.
     * @param winner nickname of the winner of the game.
     */
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

    /**
     * Ask the nickname to the player.
     */
    @Override
    public void askPlayerNickname() {
        Platform.runLater(() -> paneTransition(observers, "login_scene.fxml"));
    }

    /**
     * Ask how many players want to play the game.
     */
    @Override
    public void askPlayersNumber() {
        PlayersNumberSceneManager playersNumberSceneManager = new PlayersNumberSceneManager();
        playersNumberSceneManager.addAllObservers(observers);
        Platform.runLater(() -> paneTransition(playersNumberSceneManager, "playersNumber_scene.fxml"));
    }

    /**
     * Ask the gameMode.
     */
    @Override
    public void askGameMode() {
        GameModeSelectSceneManager gameModeSelectSceneManager = new GameModeSelectSceneManager();
        gameModeSelectSceneManager.addAllObservers(observers);
        Platform.runLater(() -> paneTransition(gameModeSelectSceneManager, "gameModeSelect_scene.fxml"));
    }

    /**
     * Method use to show the players in the lobby.
     * @param playersNickname nicknames of the player.
     * @param numPlayers number of players to be shown.
     */
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

    /**
     * Method used to notify all the players that someone has disconnected.
     * @param nicknameDisconnected nick of the player that has disconnected.
     */
    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected) {
        Platform.runLater( () -> {
            showGenericMessage("FINE DELLA PARTITA", "Il gicatore " + nicknameDisconnected + " si è disconnesso.");
            //paneTransition(observers, "scene_menu.fxml");
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Platform.runLater( () -> {
            showErrorMessage("ERROR", errorMessage);
            //paneTransition(observers, "scene_menu.fxml");
        } );
    }

    /**
     * Method used to print the state of the game.
     * It shows alle the island and all the scoreboard.
     *
     * @param gameSerialized reduced version of the game.
     */
    @Override
    public void showGameScenario(GameSerialized gameSerialized) {
        //getAndPaneTransitionGameScenario();
        Platform.runLater( () -> {
            getAndPaneTransitionGameScenario();
            mapSceneManager.updateValues(gameSerialized);
            if(scoreboardSceneManager==null) {
                showScoreboards(null);
            }
            scoreboardSceneManager.updateValues(gameSerialized);
        });
    }

    /**
     * Show which island are merged.
     * @param unifiedIsland two id of the islands.
     */
    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland) {
        //max 2 alla volta
        System.out.println("ISLAND MERGE");
        Integer minValue = 12;
        for (Integer integer : unifiedIsland)
            if (integer< minValue) minValue = integer+1;
        if(minValue == 1 && (unifiedIsland.get(0) == 11 || unifiedIsland.get(1) == 11))
            minValue = 12;
        Integer finalMinValue = minValue;
        Platform.runLater( () -> mapSceneManager.build(finalMinValue) );
    }

    /**
     * Method to ask if the player want to use a character card.
     * @param characterCards list of available character cards.
     */
    @Override
    public void askCharacterCard(List<CharacterCard> characterCards) {
        new Thread( () -> notifyObserver(obs -> obs.onUpdateUseEffect(false))).start();
    }

    /**
     * Method used to show the info of the game.
     * @param playersNickname nicknames of the players in game.
     * @param unifiedIslandsNumber number of unified islands.
     * @param remainingBagStudents bag students remaining.
     * @param activePlayer player in turn.
     * @param characterCards avaiable character cards.
     */
    @Override
    public void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {

    }

    /**
     * Method used to show the info of the game.
     * @param playersNicknames nicknames of the players in game
     * @param length
     * @param size
     * @param activePlayer player in turn
     */
    @Override
    public void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer) {

    }

    /**
     * Update a generic message sent by the server.
     * @param genericMessage message to print.
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater( () -> {
            //SceneManager.showGenericMessage("INFO", genericMessage);
            //SceneManager.paneTransition(observers, "scene_menu.fxml");
        } );
    }

    /**
     * Ask chose an assistant card.
     * @param assistantCards assistantCards in hand.
     * @param playedAssistantCards cards already played in this turn by the other players.
     */
    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards) {
        Platform.runLater( () -> {
            mapSceneManager.setActionFase(false);
            mapSceneManager.setActive(true);
            mapSceneManager.setPlayedAssistantCardsList(playedAssistantCards);
            mapSceneManager.setAssistants(assistantCards);
            mapSceneManager.enableAssistant(assistantCards);
            if(playedAssistantCards.size() == 0) {
                mapSceneManager.hideOpCard1();
                mapSceneManager.hideOpCard2();
                mapSceneManager.disableOpcard1();
                mapSceneManager.disableOpcard2();
            }
            if(playedAssistantCards.size() == 1) {
                mapSceneManager.setOpponent1Card(playedAssistantCards.get(0).getValue());
                mapSceneManager.enableOpcard1();
                mapSceneManager.disableOpcard2();
                mapSceneManager.hideOpCard2();

            }
            if(playedAssistantCards.size() == 2) {
                mapSceneManager.setOpponent1Card(playedAssistantCards.get(0).getValue());
                mapSceneManager.setOpponent2Card(playedAssistantCards.get(1).getValue());
                mapSceneManager.enableOpcard1();
                mapSceneManager.enableOpcard2();
            }
            showGenericMessage("Scelta della carta", "Scegli la carta assistente!");
        });
    }

    /**
     * Ask to the player to move a student, so which student to move and were.
     * @param studentDiscs student to move.
     * @param position where to move the student (0 dining room, 1 island).
     * @param islandNumber if position = 1, chose the island to move the student.
     */
    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {
        Platform.runLater( () -> {
            mapSceneManager.setActionFase(true);
            mapSceneManager.setActive(true);
            showGenericMessage("Spostamento", "Muovi uno studente dall'entrata");
            scoreboardSceneManager.enableEntrance();
        } );
    }

    /**
     * Ask how many steps move motherNature forward.
     * @param maxSteps max step the player can do.
     */
    @Override
    public void askToMoveMotherNature(int maxSteps) {
        Platform.runLater( () -> {
            mapSceneManager.setActive(true);
            showGenericMessage("Madre natura", "Muovi madre natura di massimo " + maxSteps + " passi!");
            mapSceneManager.enableMotherNature(maxSteps);
        } );
    }

    /**
     * Ask to choose a cloud to pick the students.
     * @param cloudList list of available clouds.
     */
    @Override
    public void askToChooseACloud(ArrayList<Cloud> cloudList) {
        Platform.runLater( () -> {
            if(cloudList.size() == 2){
                mapSceneManager.clearCloud1();
                mapSceneManager.clearCloud2();
                mapSceneManager.addStudentsToCloud(cloudList.get(0),1);
                mapSceneManager.addStudentsToCloud(cloudList.get(1),2);
                mapSceneManager.enableClouds();}

            showGenericMessage("Nuvole", "Scegli la nuvola che fa per te!");
        });
    }

    /**
     * Show the result of the loginRequest.
     * @param nickname nickname.
     * @param playerNicknameAccepted bool true if accepted.
     * @param connectionSuccessful bool true if connected.
     */
    @Override
    public void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful) {
        if (!playerNicknameAccepted || !connectionSuccessful) {
            if(!playerNicknameAccepted && connectionSuccessful) {
                Platform.runLater( () -> {
                    showGenericMessage("Errore", "Nickname già esistente. Prova con un altro.");
                    paneTransition(observers, "login_scene.fxml");
                });
            }
            else {
                Platform.runLater( ( ) -> {
                    showErrorMessage("Errore", "Impossibile contattare il server. Riprova.");
                    paneTransition(observers, "login_scene.fxml");
                });
            }
        }
    }

    /**
     * Print a victory message to notify the end of the game.
     * @param winner nick of the player that has won.
     */
    @Override
    public void showVictoryMessage(String winner) {
        Platform.runLater( () -> {
            showWinner(winner);
            paneTransition(observers, "victory_scene.fxml");
        });
    }

    /**
     * Method used to notify all the players that someone has reconnected.
     * @param nicknameReconnecting nick of the player that has reconnected.
     */
    @Override
    public void showReconnectedMessage(String nicknameReconnecting) {
        showGenericMessage("GAME", nicknameReconnecting + "si è riconnesso al gioco!");
    }
}
