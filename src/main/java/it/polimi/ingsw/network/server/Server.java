package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.CloudNotEmptyException;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.exception.MissingPlayersException;
import it.polimi.ingsw.view.VirtualView;

import java.util.HashMap;
import java.util.Map;

public class Server {
    private final GameController gameController;

    private final Map<String, ClientHandler> clientHandlerMap;

    public Server(GameController gameController) {
        this.gameController = gameController;
        this.clientHandlerMap = new HashMap<>();
    }

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

    public void removeClient(String nickname) {
        clientHandlerMap.remove(nickname);
        gameController.getVirtualViewMap().remove(nickname);
        gameController.showGenericMessageToAll("Player " + nickname + " disconnected!");
    }

    public void onDisconnect(ClientHandler clientHandler) {

    }

    public GameState getGameState(){
        return gameController.getGameState();
    }

    public String getNicknameFromClientHandler(ClientHandler clientHandler){
        return "";
    }

    public GameController getGameController() {
        return gameController;
    }

}
