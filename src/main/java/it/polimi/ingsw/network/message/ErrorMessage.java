package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class ErrorMessage extends Message {
    private static final long serialVersionUID = 1002L;
    private final String error;

    public ErrorMessage(String error) {
        super(Game.SERVER_NAME, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "ErrorMessage[nickname:" + getNickname() + ", error:" + error + "]";
    }
}
