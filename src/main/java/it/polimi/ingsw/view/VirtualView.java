package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.ErrorMessage;
import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

public class VirtualView implements View, Observer {
    private ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void showMessage(String messageString) {
        clientHandler.sendMessage(new ErrorMessage(messageString));
    }

    @Override
    public void update(MoveMotherNatureMessage message){
        clientHandler.sendMessage(message);
    }

}