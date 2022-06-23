package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class VictoryCheckMessage extends Message {
    private static final long serialVersionUID = 2456845241456325457L;

    public VictoryCheckMessage() {
        super(Game.SERVER_NAME, MessageType.WIN_CHECK);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "VictoryCheckMessage[ ]";
    }
}
