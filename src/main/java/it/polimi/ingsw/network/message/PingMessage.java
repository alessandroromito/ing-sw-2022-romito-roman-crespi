package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Ping-type message.
 */
public class PingMessage extends Message {
    @Serial
    private static final long serialVersionUID = -5571992292972014289L;

    /**
     * Default constructor.
     */
    public PingMessage() {
        super(null, MessageType.PING);
    }

    /**
     * This method communicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }
}
