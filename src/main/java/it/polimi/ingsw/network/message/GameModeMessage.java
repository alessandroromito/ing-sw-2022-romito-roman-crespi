package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

public class GameModeMessage extends Message{
    @Serial
    private static final long serialVersionUID = -1738530720638559500L;

    private final boolean expertMode;

    public GameModeMessage(boolean expertMode) {
        super(Game.SERVER_NAME, MessageType.GAME_MODE);
        this.expertMode = expertMode;
    }

    public boolean getExpertMode() {
        return expertMode;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "GameModeMessage[" + "nickname:" + getNickname() + ", expertMode:" + this.expertMode + "]";
    }
}
