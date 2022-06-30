package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Message that contains the request of the player reconnecting from a possible crash.
 */
public class ReconnectingMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8577406039152802393L;

    private final String nicknameReconnecting;

    /**
     * Default constructor.
     * @param nicknameReconnecting nickname of the player reconnecting.
     */
    public ReconnectingMessage(String nicknameReconnecting) {
        super(Game.SERVER_NAME, MessageType.RECONNECTING_MESSAGE);
        this.nicknameReconnecting = nicknameReconnecting;
    }


    /**
     * @return nickname of the player reconnecting.
     */
    public String getNicknameReconnecting() {
        return nicknameReconnecting;
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
        return "ReconnectingMessage[" + "nicknameReconnecting:" + getNicknameReconnecting() + "]";
    }
}
