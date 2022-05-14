package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.ErrorMessage;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

public class VirtualView implements View, Observer {
    private ClientHandler clientHandler;

    /**
     * Default constructor.
     *
     * @param clientHandler the clientHandler to send messages
     */
    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void showMessage(String nickName, String messageString) {
        clientHandler.sendMessage(new ErrorMessage(nickName, messageString));
    }

    /**
     * Receives a message from the model.
     * The action will be taken by the view on the client side.
     *
     * @param message the message created by the model
     */
    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

    @Override
    public void askPlayersNumber() {

    }

    public void askToMoveStudent() {
        clientHandler.sendMessage(new GenericMessage("Please chose a student to move..."));
    }
}