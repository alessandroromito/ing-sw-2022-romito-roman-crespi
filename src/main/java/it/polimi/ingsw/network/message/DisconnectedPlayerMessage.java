package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

public class DisconnectedPlayerMessage extends Message {
    @Serial
    private static final long serialVersionUID = -4591163805644038090L;

    private final String nicknameDisconnected;

    public DisconnectedPlayerMessage(String nicknameDisconnected) {
        super(Game.SERVER_NAME, MessageType.DISCONNECTED_PLAYER);
        this.nicknameDisconnected = nicknameDisconnected;
    }

    public String getNicknameDisconnected() {
        return nicknameDisconnected;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "DisconnectedPlayerMessage[nicknameDisconnected:" + nicknameDisconnected + "]";
    }
}
