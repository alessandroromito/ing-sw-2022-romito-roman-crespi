package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.view.VirtualView;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.server.extra.ANSICostants.ANSI_RED;
import static it.polimi.ingsw.server.extra.ANSICostants.ANSI_RESET;

public class Server {
    private final GameController gameController;
    private final Map<String, ClientHandler> clientHandlerMap;

    private final Object lock;

    public Server(GameController gameController) {
        this.gameController = gameController;
        this.clientHandlerMap = new HashMap<>();
        this.lock = new Object();
    }

    public void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView virtualView = new VirtualView(clientHandler);

        if(gameController.checkLoginNickname(nickname)){
            gameController.addPlayer(nickname, virtualView);
            clientHandlerMap.put(nickname, clientHandler);
        }
        else{
            clientHandler.disconnect();
        }
    }

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

    public void removeClient(String nickname) {
        clientHandlerMap.remove(nickname);
        gameController.getVirtualViewMap().remove(nickname);
        if(getGameState() == GameState.GAME_STARTED)
            gameController.getTurnController().getVirtualViewMap().remove(nickname);
    }

    public GameState getGameState(){
        return gameController.getGameState();
    }

    public String getNicknameFromClientHandler(ClientHandler clientHandler){
        return clientHandler.getNickname();
    }

    public GameController getGameController() {
        return gameController;
    }

}
