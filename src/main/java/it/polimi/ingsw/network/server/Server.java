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

    private boolean resumeGame;

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

        if(gameController.checkLoginNickname(nickname)){
            gameController.addPlayer(nickname, virtualView);
            clientHandlerMap.put(nickname, clientHandler);
        }
        else{
            virtualView.showGenericMessage("Nickname non valido!");
            virtualView.askPlayerNickname();
        }
    }

    /**
     * Synchronized handler of a client disconnection.
     * @param clientHandler clientHandler associated to the client of the disconnection request.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        if(gameController.getGame() != null && gameController.getGame().getPlayersConnected().size() == 1) {
            gameController.endGame();
            return;
        }
        synchronized (lock) {
            String nickname = getNicknameFromClientHandler(clientHandler);
            if(gameController.getGame() != null && gameController.getGameState() == GameState.IN_GAME)
                gameController.getReconnectingPlayersList().add(nickname);

            removeClient(nickname);
            System.out.println(nickname + " removed");

            if(nickname != null) {
                gameController.showDisconnectionMessage(nickname);

                if (gameController.getGameState() == GameState.IN_GAME) {

                    //Set player as disconnected in Game
                    gameController.getGame().getPlayerByNickname(nickname).setConnected(false);
                    System.out.println(ANSI_RED + nickname + " set not connected in game" + ANSI_RESET);

                    System.out.println(ANSI_RED + "Remaining connected players: " + gameController.getGame().getPlayersConnected().size() + ANSI_RESET);
                    switch (gameController.getGame().getPlayersConnected().size()){

                        case 0 -> gameController.endGame();
                        case 1 -> {
                            gameController.setInPause(true);
                            gameController.setResumeGame(false);

                            long start = System.currentTimeMillis();
                            long end = start + 30 * 1000;
                            System.out.println("Waiting 30 sec for reconnecting...");
                            while (System.currentTimeMillis() < end && !gameController.resumeGame()) {
                                // wait
                            }

                            if(!gameController.resumeGame()){
                                if(gameController.getGame() != null && gameController.getGame().getPlayersConnected().size() == 1) {
                                    gameController.win(gameController.getGame().getPlayersConnected().get(0));
                                }
                            }

                        }
                        case 2 -> {
                            //continue game
                            if(gameController.getTurnController().getActivePlayer().equals(nickname)){
                                gameController.getTurnController().next();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove a client associated to a certain nickname.
     * @param nickname nickname of the client to remove.
     */
    public void removeClient(String nickname) {
        clientHandlerMap.remove(nickname);
        gameController.removeVirtualView(nickname);

        if(getGameState() == GameState.GAME_ROOM){
            gameController.getPlayersNicknames().remove(nickname);
        }
    }

    /**
     * @return the game state from GameController.
     */
    public GameState getGameState(){
        return gameController.getGameState();
    }

    /**
     * @param clientHandler clientHandler of the client got from the clientHandlerMap.
     * @return nickname of the client associated to clientHandler.
     */
    public String getNicknameFromClientHandler(ClientHandler clientHandler){
        for(Map.Entry<String, ClientHandler> entry : clientHandlerMap.entrySet()){
            if(clientHandler == entry.getValue())
                return entry.getKey();
        }
        return null;
    }

    /**
     * @return gameController
     */
    public GameController getGameController() {
        return gameController;
    }

}
