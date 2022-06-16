package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

public class GameModeReplyMessage extends Message{
    private static final long serialVersionUID = -1L; //da scegliere

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
