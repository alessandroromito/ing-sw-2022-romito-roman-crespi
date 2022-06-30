package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Error message.
 */
public class ErrorMessage extends Message {
    @Serial
    private static final long serialVersionUID = -1873207868219647630L;

    private final String error;

    /**
     * Default constructor.
     * @param error text to be shown in the message.
     */
    public ErrorMessage(String error) {
        super(Game.SERVER_NAME, MessageType.ERROR);
        this.error = error;
    }

    /**
     * @return text to be shown in the message.
     */
    public String getError() {
        return error;
    }

    /**
     * This method comunicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "ErrorMessage[nickname:" + getNickname() + ", error:" + error + "]";
    }
}
