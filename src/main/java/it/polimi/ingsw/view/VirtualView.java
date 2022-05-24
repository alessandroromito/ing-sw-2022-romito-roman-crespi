package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.List;

public class VirtualView implements View, Observer {
    private ClientHandler clientHandler;

    /**
     * Default constructor.
     *
     * @param clientHandler the clientHandler to send messages
     */
    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void showMessage(String nickName, String messageString) {
        clientHandler.sendMessage(new ErrorMessage(nickName, messageString));
    }

    /**
     * Receives a message from the model.
     * The action will be taken by the view on the client side.
     *
     * @param message the message created by the model
     */
    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

    @Override
    public void askPlayersNumber() {
        clientHandler.sendMessage(new PlayerNumberRequest());
    }

    @Override
    public void askGameMode() {
        clientHandler.sendMessage(new GameModeMessage(false));
    }

    @Override
    public void askPlayerNickname() {
        clientHandler.sendMessage(new LoginReply(false, true));
    }

    @Override
    public void showLobby(List<String> playersNickname, int numMaxPlayers) {
        clientHandler.sendMessage(new LobbyMessage(playersNickname, numMaxPlayers));
    }

    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected, String text) {
        clientHandler.sendMessage(new DisconnectedPlayerMessage(nicknameDisconnected, text));
    }

    @Override
    public void showErrorMessage(String error) {
        clientHandler.sendMessage(new ErrorMessage(Game.SERVER_NAME, error));
    }

    @Override
    public void showGameScenario() {
        //da implementare in base alle esigenze
    }

    @Override
    public void showGameInfo(List<String> playersNicknames, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {
        clientHandler.sendMessage(new ExpertGameInfoMessage(Game.SERVER_NAME, playersNicknames, unifiedIslandsNumber, remainingBagStudents, activePlayer, characterCards));
    }
    @Override
    public void showGameInfo(List<String> playersNicknames, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer) {
        clientHandler.sendMessage(new GameInfoMessage(Game.SERVER_NAME, playersNicknames, unifiedIslandsNumber, remainingBagStudents, activePlayer));
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        clientHandler.sendMessage(new AssistantCardMessage(Game.SERVER_NAME, assistantCards));
    }

    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {
        clientHandler.sendMessage(new MoveStudentMessage(Game.SERVER_NAME, studentDiscs, position, islandNumber));
    }

    @Override
    public void askToMoveMotherNature(int steps) {
        clientHandler.sendMessage(new MoveMotherNatureMessage(Game.SERVER_NAME, steps));
    }

    @Override
    public void askToChooseACloud(List<Cloud> cloudList) {
        clientHandler.sendMessage(new CloudMessage(Game.SERVER_NAME, cloudList));
    }

    @Override
    public void showLoginResult(boolean playerNicknameAccepted, boolean connectionSuccessful, String nickname) {
        clientHandler.sendMessage(new LoginReply(playerNicknameAccepted, connectionSuccessful));
    }

    @Override
    public void showVictoryMessage(String winner) {
        clientHandler.sendMessage(new VictoryMessage(winner));
    }
}