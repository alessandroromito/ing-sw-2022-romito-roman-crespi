package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.server.enumerations.GameState;

/**
 * Class that handle the messages received by the client
 */
public class MessageHandler {

    private GameController gameController; // to take action in the game

    /**
     * Default Constructor
     *
     * @param socketServer
     */
    public MessageHandler(SocketServer socketServer) {
        this.gameController = socketServer.getGameController();
    }

    /**
     * Client don't send PlayerNumberRequest
     * @param playerNumberRequest
     */
    public void handleMessage(PlayerNumberRequest playerNumberRequest) {

    }

    public void handleMessage(PlayerNumberReply message){
        if(gameController.getGameState() == GameState.GAME_ROOM) {
            gameController.setChosenPlayerNumber(message);
        }
    }

    public void handleMessage(GameModeMessage message){
        if(gameController.getGameState() == GameState.GAME_ROOM) {
            gameController.setChosenExpertMode(message);
        }
    }

    public void handleMessage(MoveStudentMessage message){
        if(gameController.getGameState() == GameState.IN_GAME){
            if(gameController.checkUser(message)){
                gameController.moveStudent(message);
            }
        }
    }

    public void handleMessage(MoveMotherNatureMessage message){
        if(gameController.getGameState() == GameState.IN_GAME){
            if(gameController.checkUser(message)){
                gameController.moveMotherNature(message);
            }
        }
    }

    public void handleMessage(CloudMessage message) {
        if(gameController.getGameState() == GameState.IN_GAME){
            if(gameController.checkUser(message)){
                gameController.pickCloud(message);
            }
        }
    }

    /**
     * Client don't send LoginReplyMessage
     * @param loginReply
     */
    public void handleMessage(LoginReply loginReply) {
    }

    /**
     * Handled by ClientHandler
     * @param pingMessage
     */
    public void handleMessage(PingMessage pingMessage) {
    }

    /**
     * Client don't send VictoryMessage
     * @param victoryMessage
     */
    public void handleMessage(VictoryMessage victoryMessage) {

    }

    /**
     * Client don't send LobbyMessage
     * @param lobbyMessage
     */
    public void handleMessage(LobbyMessage lobbyMessage) {
    }

    public void handleMessage(DisconnectedPlayerMessage disconnectedPlayerMessage) {

    }

    /**
     * Client don't send ErrorMessage
     * @param errorMessage
     */
    public void handleMessage(ErrorMessage errorMessage) {

    }

    /**
     * Client don't send GenericMessage
     * @param message
     */
    public void handleMessage(GenericMessage message) {
    }

    /**
     * Client don't send MergeIslandMessage
     * @param mergeIslandMessage
     */
    public void handleMessage(MergeIslandMessage mergeIslandMessage) {

    }

    public void handleMessage(GameInfoMessage gameInfoMessage) {
        if(gameController.getGameState() == GameState.IN_GAME){
            gameController.sendInfo(gameInfoMessage);
        }
    }

    public void handleMessage(ExpertGameInfoMessage expertGameInfoMessage) {
        if(gameController.getGameState() == GameState.IN_GAME){
            gameController.sendInfo(expertGameInfoMessage);
        }
    }

    public void handleMessage(AssistantCardMessage assistantCardMessage) {
        if(gameController.getGameState() == GameState.IN_GAME){
            if(gameController.checkUser(assistantCardMessage)){
                gameController.setAssistantCard(assistantCardMessage);
            }
        }
    }

    public void handleMessage(UseEffectMessage useEffectMessage) {
        if(gameController.getGameState() == GameState.IN_GAME){
            if(gameController.checkUser(useEffectMessage)){
                gameController.applyEffect(useEffectMessage);
            }
        }
    }

    /**
     * Client don't send LoginRequest
     * @param loginRequest
     */
    public void handleMessage(LoginRequest loginRequest) {
    }
}
