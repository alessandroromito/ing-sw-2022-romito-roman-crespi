package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

public abstract class ClientMessage extends Message {
    public ClientMessage(String nickname, MessageType message) {
        super(nickname,message);
    }

    public abstract void handle(MessageHandler messageHandler);
}
