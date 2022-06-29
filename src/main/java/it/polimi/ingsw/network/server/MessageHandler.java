package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.server.enumerations.GameState;

/**
 * Class that handle the messages received by the client
 */
public class MessageHandler {

    private final GameController gameController;

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

    /**
     * Handle a PlayerNumberReplyMessage sent by the client
     * @param message
     */
    public void handleMessage(PlayerNumberReply message){
        if(gameController.getGameState() == GameState.GAME_ROOM) {
            gameController.setChosenPlayerNumber(message);
        }
    }

    /**
     * Client don't send GameModeMessage
     * @param message
     */
    public void handleMessage(GameModeMessage message){

    }

    /**
     * Handle a GameModeReplyMessage sent by the client
     * @param message
     */
    public void handleMessage(GameModeReplyMessage message) {
        if(gameController.getGameState() == GameState.GAME_ROOM) {
            gameController.setChosenExpertMode(message);
        }
    }

    /**
     * Handle a MoveStudentMessage sent by the client
     * @param message
     */
    public void handleMessage(MoveStudentMessage message){
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.moveStudent(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a MoveMotherNatureMessage sent by the client
     * @param message
     */
    public void handleMessage(MoveMotherNatureMessage message){
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.moveMotherNature(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a CloudMessage sent by the client
     * @param message
     */
    public void handleMessage(CloudMessage message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.pickCloud(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Client don't send LoginReplyMessage
     * @param message
     */
    public void handleMessage(LoginReply message) {
    }

    /**
     * Handled by ClientHandler
     * @param message
     */
    public void handleMessage(PingMessage message) {
    }

    /**
     * Client don't send VictoryMessage
     * @param message
     */
    public void handleMessage(VictoryMessage message) {

    }

    /**
     * Client don't send LobbyMessage
     * @param lobbyMessage
     */
    public void handleMessage(LobbyMessage lobbyMessage) {

    }

    /**
     * Handle a DisconnectedPlayerMessage sent by the client
     * @param message
     */
    public void handleMessage(DisconnectedPlayerMessage message) {

    }

    /**
     * Client don't send ErrorMessage
     * @param message
     */
    public void handleMessage(ErrorMessage message) {

    }

    /**
     * Client don't send GenericMessage
     * @param message
     */
    public void handleMessage(GenericMessage message) {
    }

    /**
     * Client don't send MergeIslandMessage
     * @param message
     */
    public void handleMessage(MergeIslandMessage message) {

    }


    /**
     * Handle a DisconnectedPlayerMessage sent by the client
     * @param message
     */
    public void handleMessage(GameInfoMessage message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            gameController.sendInfo(message);
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a DisconnectedPlayerMessage sent by the client
     * @param message
     */
    public void handleMessage(ExpertGameInfoMessage message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            gameController.sendInfo(message);
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a DisconnectedPlayerMessage sent by the client
     * @param message
     */
    public void handleMessage(AssistantCardMessage message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                System.out.println("Handling AssistantCardMessage");
                gameController.setAssistantCard(message);
            }
        }
        else System.out.println("SCARTATO " + message);

    }

    /**
     * Handled by ClientHandler
     * @param loginRequest
     */
    public void handleMessage(LoginRequest loginRequest) {

    }


    /**
     * Handle a Card209Message sent by the client
     * @param message
     */
    public void handleMessage(Card209Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card209Message sent by the client
     * @param message
     */
    public void handleMessage(Card210Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card211Message sent by the client
     * @param message
     */
    public void handleMessage(Card211Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card212Message sent by the client
     * @param message
     */
    public void handleMessage(Card212Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card213Message sent by the client
     * @param message
     */
    public void handleMessage(Card213Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card214Message sent by the client
     * @param message
     */
    public void handleMessage(Card214Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card216Message sent by the client
     * @param message
     */
    public void handleMessage(Card216Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card217Message sent by the client
     * @param message
     */
    public void handleMessage(Card217Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Handle a Card219Message sent by the client
     * @param message
     */
    public void handleMessage(Card219Message message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.applyEffect(message);
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Client don't send GameScenarioMessages
     * @param message
     */
    public void handleMessage(GameScenarioMessage message) {

    }

    /**
     * Client don't send CharacterCardMessage
     * @param message
     */
    public void handleMessage(CharacterCardMessage message) {
    }

    /**
     * Handle a CharacterCardReplyMessage sent by the client
     * @param message
     */
    public void handleMessage(CharacterCardReplyMessage message) {
        if(gameController.getGameState() == GameState.IN_GAME && !gameController.isInPause()){
            if(gameController.checkUser(message)){
                gameController.noApplyEffect();
            }
        }
        else System.out.println("SCARTATO " + message);
    }

    /**
     * Client don't send CharacterCardMessage
     * @param message
     */
    public void handleMessage(VictoryCheckMessage message) {
    }

    /**
     * Client don't send CharacterCardMessage
     * @param message
     */
    public void handleMessage(ReconnectingMessage message) {

    }
}
