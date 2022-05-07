package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.LoginRequest;
import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.network.message.PlayerNumberRequest;
import it.polimi.ingsw.server.enumerations.GameState;

/**
 * Class that handle the messages received by the client
 */
public class MessageHandler {

    private final SocketServer socketServer; //to take action in the server
    private final ClientHandler clientHandler; //to send messages in return
    private GameController gameController; // to take action in the game

    public MessageHandler(SocketServer socketServer, ClientHandler clientHandler) {
        this.socketServer = socketServer;
        this.clientHandler = clientHandler;
        this.gameController = socketServer.getGameController();
    }

    /**
     * Handle the login request arrived by the client
     *
     * @param message LoginRequest
     */
    public void handleMessage(LoginRequest message){
        socketServer.addClient(message.getNickname(), clientHandler);
    }

    public void handleMessage(PlayerNumberRequest message){

    }

    public void handleMessage(MoveMotherNatureMessage message){
        if(gameController.getGameState() == GameState.IN_GAME){
            if(gameController.checkUser(message)){
                gameController.moveMotherNature(message);
            }
        }
    }
}
