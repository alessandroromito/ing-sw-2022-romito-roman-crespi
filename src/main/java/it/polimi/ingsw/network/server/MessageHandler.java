package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.GameModeMessage;
import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.network.message.MoveStudentMessage;
import it.polimi.ingsw.network.message.PlayerNumberReply;
import it.polimi.ingsw.server.enumerations.GameState;

/**
 * Class that handle the messages received by the client
 */
public class MessageHandler {

    private final SocketServer socketServer; //to take action in the server
    private final ClientHandler clientHandler; //to send messages in return
    private GameController gameController; // to take action in the game

    /**
     * Default Constructor
     *
     * @param socketServer
     * @param clientHandler
     */
    public MessageHandler(SocketServer socketServer, ClientHandler clientHandler) {
        this.socketServer = socketServer;
        this.clientHandler = clientHandler;
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
}
