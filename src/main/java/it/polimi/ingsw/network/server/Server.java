package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.view.VirtualView;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import static it.polimi.ingsw.server.extra.ANSICostants.ANSI_RED;
import static it.polimi.ingsw.server.extra.ANSICostants.ANSI_RESET;

/**
 * Main server class.
 * Able to start a socket server.
 * It handles number of connection > 1 during game phase.
 */
public class Server {
    private final GameController gameController;
    private final Map<String, ClientHandler> clientHandlerMap;
    private final Object lock;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(GameController gameController) {
        this.gameController = gameController;
        this.clientHandlerMap = new HashMap<>(); //or synchronized map?
        this.lock = new Object();
    }

    /**
     * Adds a client to be managed from server instance.
     * @param nickname nickname associated to the client.
     * @param clientHandler client handler associated to the client.
     */
    public void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView virtualView = new VirtualView(clientHandler);

        try {
            if(gameController.checkLoginNickname(nickname)){
                gameController.addPlayer(nickname, virtualView);
                clientHandlerMap.put(nickname, clientHandler);
            }
            else{
                clientHandler.disconnect();
            }
        } catch (MissingPlayersException | MissingPlayerNicknameException | InvalidActionPhaseStateException |
                 InterruptedException | CloudNotEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove a client associated to a certain nickname.
     * @param nickname nickname of the client to remove.
     */
    public void removeClient(String nickname) {
        clientHandlerMap.remove(nickname);
        gameController.getVirtualViewMap().remove(nickname);
        System.out.println(nickname + "rimosso dalla lista dei client.");
=======

        if(gameController.checkLoginNickname(nickname)){
            gameController.addPlayer(nickname, virtualView);
            clientHandlerMap.put(nickname, clientHandler);
        }
        else{
            clientHandler.disconnect();
        }
    }

    /**
     * Synchronized handler of a client disconnection.
     * @param clientHandler clientHandler associated to the client of the disconnection request.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        synchronized (lock) {
            String nickname = getNicknameFromClientHandler(clientHandler);

            System.out.println(ANSI_RED + "removeClient(" + nickname + ')' + ANSI_RESET);

            removeClient(nickname);

            if(nickname != null) {
                if (gameController.getGameState() == GameState.GAME_STARTED) {

                }
                else{
                    gameController.showDisconnectionMessage(nickname, " disconnected from the server!");
                }
            }
        }
    }


    /**
     * @return the game state from GameController.
     */
    public void removeClient(String nickname) {
        clientHandlerMap.remove(nickname);
        gameController.getVirtualViewMap().remove(nickname);
        if(getGameState() == GameState.GAME_STARTED)
            gameController.getTurnController().getVirtualViewMap().remove(nickname);
    }

  
    public GameState getGameState(){
        return gameController.getGameState();
    }

    /**
     * @param clientHandler clientHandler of the client got from the clientHandlerMap.
     * @return nickname of the client associated to clientHandler.
     */
    public String getNicknameFromClientHandler(ClientHandler clientHandler){
        return clientHandler.getNickname();
    }

    /**
     * @return gameController
     */
    public GameController getGameController() {
        return gameController;
    }

}
