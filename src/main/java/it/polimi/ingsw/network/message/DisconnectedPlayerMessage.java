package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Message that notice to players that one player has been disconnected.
 */
public class DisconnectedPlayerMessage extends Message {
    @Serial
    private static final long serialVersionUID = -4591163805644038090L;

    private final String nicknameDisconnected;

    /**
     * @param nicknameDisconnected nickname of the player disconnected.
     */
    public DisconnectedPlayerMessage(String nicknameDisconnected) {
        super(Game.SERVER_NAME, MessageType.DISCONNECTED_PLAYER);
        this.nicknameDisconnected = nicknameDisconnected;
    }

    /**
     * @return nickname of the player disconnected.
     */
    public String getNicknameDisconnected() {
        return nicknameDisconnected;
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
        return "DisconnectedPlayerMessage[nicknameDisconnected:" + nicknameDisconnected + "]";
    }
}
