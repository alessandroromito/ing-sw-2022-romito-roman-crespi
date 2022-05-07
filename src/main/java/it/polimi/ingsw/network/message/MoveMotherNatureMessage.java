package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class MoveMotherNatureMessage extends Message {
    private static final long serialVersionUID = -1L; //da scegliere

    private final int steps;

    public MoveMotherNatureMessage(String nickname, int steps) {
        super(nickname, MessageType.MOVE_MOTHER_NATURE);
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage[" + "nickname:" + getNickname() + ", steps:" + this.steps + "]";
    }

}
