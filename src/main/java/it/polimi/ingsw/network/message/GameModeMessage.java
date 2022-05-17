package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class GameModeMessage extends Message{
    private static final long serialVersionUID = -1L; //da scegliere

    private final boolean expertMode;

    public GameModeMessage(String nickname, boolean expertMode) {
        super(nickname, MessageType.GAME_MODE);
        this.expertMode = expertMode;
    }

    public boolean getExpertMode() {
        return expertMode;
    }

    @Override
    public String toString() {
        return "GameModeMessage[" + "nickname:" + getNickname() + ", expertMode:" + this.expertMode + "]";
    }
}
