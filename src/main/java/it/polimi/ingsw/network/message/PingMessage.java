package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

public class PingMessage extends Message {
    private static final long serialVersionUID = 1L; //da scegliere

    public PingMessage() {
        super(null, MessageType.PING);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }
}
