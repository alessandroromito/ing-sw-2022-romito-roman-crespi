package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

public class PingMessage extends Message {
    @Serial
    private static final long serialVersionUID = -5571992292972014289L;

    public PingMessage() {
        super(null, MessageType.PING);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }
}
