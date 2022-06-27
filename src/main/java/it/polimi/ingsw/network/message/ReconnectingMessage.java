package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class ReconnectingMessage extends Message {
    private static final long serialVersionUID = 112456L; //da scegliere

    private final String nicknameReconnecting;

    public ReconnectingMessage(String nicknameReconnecting) {
        super(Game.SERVER_NAME, MessageType.RECONNECTING_MESSAGE);
        this.nicknameReconnecting = nicknameReconnecting;
    }


    public String getNicknameReconnecting() {
        return nicknameReconnecting;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "ReconnectingMessage[" + "nicknameReconnecting:" + getNicknameReconnecting() + "]";
    }
}
