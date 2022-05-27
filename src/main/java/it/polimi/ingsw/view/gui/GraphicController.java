package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
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
            Logger.getLogger("client").severe(e.getMessage());
            return null;
        }

        return fxmlLoader.getController();
    }

    @Override
    public void askPlayersNumber() {
        Platform.runLater(() -> SceneManager.paneTransition(observers, "login_scene.fxml"));
    }

    @Override
    public void askGameMode() {

    }

    @Override
    public void askPlayerNickname() {

    }

    @Override
    public void showLobby(List<String> playersNickname, int numPlayers) {

    }

    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected, String text) {

    }

    @Override
    public void showErrorMessage(String error) {

    }

    @Override
    public void showGameScenario() {

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
    public void showLoginResult(boolean playerNicknameAccepted, boolean connectionSuccessful, String nickname) {

    }

    @Override
    public void showVictoryMessage(String winner) {

    }
}
