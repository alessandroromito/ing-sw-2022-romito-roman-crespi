package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
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

    }

    @Override
    public void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    @Override
    public void askAssistantCard() {

    }

    @Override
    public void askToMoveAStudent() {

    }

    @Override
    public void askToMoveMotherNature() {

    }

    @Override
    public void askToChooseACloudAndTake3Students(List<Cloud> cloudList) {

    }

    @Override
    public void showLoginResult(boolean playerNicknameAccepted, boolean connectionSuccessful, String playerNickname) {

    }

    @Override
    public void showVictoryMessage(String winner) {
        clientHandler.sendMessage(new VictoryMessage(winner));
    }
}