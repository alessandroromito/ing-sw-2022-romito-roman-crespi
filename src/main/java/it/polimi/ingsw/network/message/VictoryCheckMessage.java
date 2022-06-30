package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Message to check if there is a winner.
 */
public class VictoryCheckMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8102891754062178924L;

    /**
     * Default constructor.
     */
    public VictoryCheckMessage() {
        super(Game.SERVER_NAME, MessageType.WIN_CHECK);
    }

    /**
     * This method communicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "VictoryCheckMessage[ ]";
    }
}
