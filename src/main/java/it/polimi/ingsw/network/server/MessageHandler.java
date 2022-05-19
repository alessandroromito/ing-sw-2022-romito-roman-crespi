package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.server.enumerations.GameState;

/**
 * Class that handle the messages received by the client
 */
public class MessageHandler {

    private final SocketServer socketServer; //to take action in the server
    private GameController gameController; // to take action in the game

    /**
     * Default Constructor
     *
     * @param socketServer
     */
    public MessageHandler(SocketServer socketServer) {
        this.socketServer = socketServer;
        this.gameController = socketServer.getGameController();
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

    public void handleMessage(LoginRequest loginRequest) {

    }

    public void handleMessage(LoginReply loginReply) {
    }

    public void handleMessage(PingMessage pingMessage) {
    }

    public void handleMessage(PlayerNumberRequest playerNumberRequest) {
    }

    public void handleMessage(VictoryMessage victoryMessage) {
    }

    public void handleMessage(LobbyMessage lobbyMessage) {
    }

    public void handleMessage(DisconnectedPlayerMessage disconnectedPlayerMessage) {
    }

    public void handleMessage(ErrorMessage errorMessage) {
    }

    public void handleMessage(GenericMessage genericMessage) {
    }

    public void handleMessage(GameInfoMessage gameInfoMessage) {
        if(gameController.getGameState() == GameState.IN_GAME){
            gameController.sendInfo(gameInfoMessage);
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

    public void handleMessage(CloudMessage cloudMessage) {
    }
}
