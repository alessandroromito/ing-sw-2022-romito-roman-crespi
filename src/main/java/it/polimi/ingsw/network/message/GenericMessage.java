package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Generic message that contains textual info.
 */
public class GenericMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8549635370892576323L;

    private final String message;

    /**
     * @param message text of the message.
     */
    public GenericMessage(String message) {
        super(Game.SERVER_NAME, MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    /**
     * @return text of the message.
     */
    public String getMessage() {
        return message;
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
        return "GenericMessage[" + "nickname:" + getNickname() + ", message:" + message + "]";
    }
}
