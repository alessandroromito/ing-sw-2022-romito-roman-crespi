package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.Message;

import java.util.Map;

public class Server {
    private final GameController gameController;
    private final Map<String, ClientHandler> clientHandlerMap;

    public void addClient(String nickname, ClientHandler clientHandler) {}

    public void removeClient(String nickname) { // mandare sempre messaggio disconnessione agli altri giocatori
        }

    public void onMessageReceived(Message message){ gameController.onMessageReceived(message);}

    public void onDisconnect(ClientHandler clientHandler) {}

    public String getNicknameFromClientHandler(ClientHandler clientHandler){return "";}

}
