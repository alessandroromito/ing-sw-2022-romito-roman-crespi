package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

public class GameModeReplyMessage extends Message{
    @Serial
    private static final long serialVersionUID = 1201849542872305093L;

    private final boolean expertMode;

    public GameModeReplyMessage(String nickname, boolean expertMode) {
        super(nickname, MessageType.GAME_MODE);
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
        return "GameModeReplyMessage[" + "nickname:" + getNickname() + ", expertMode:" + this.expertMode + "]";
    }
}
