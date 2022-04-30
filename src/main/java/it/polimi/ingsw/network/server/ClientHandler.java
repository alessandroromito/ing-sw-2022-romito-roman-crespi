package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

public interface ClientHandler {
    //connection status
    boolean isConnected();

    //disconnect from the client
    void disconnect();

    //send a message to the client
    void sendMessage(Message message);
}
