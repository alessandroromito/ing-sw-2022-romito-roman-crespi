package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

public class MoveMotherNatureMessage extends Message {
    @Serial
    private static final long serialVersionUID = -539625891352821L;

    private final int steps;

    public MoveMotherNatureMessage(String nickname, int steps) {
        super(nickname, MessageType.MOVE_MOTHER_NATURE);
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage[" + "nickname:" + getNickname() + ", steps:" + this.steps + "]";
    }

}
