package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Message that contains information to move mother nature.
 */
public class MoveMotherNatureMessage extends Message {
    @Serial
    private static final long serialVersionUID = 7105477244414011103L;

    private final int steps;

    /**
     * Defaul constructor.
     * @param nickname of the owner of the message and requester of moving mother nature.
     * @param steps steps that mother nature need to do.
     */
    public MoveMotherNatureMessage(String nickname, int steps) {
        super(nickname, MessageType.MOVE_MOTHER_NATURE);
        this.steps = steps;
    }

    /**
     * @return the steps that mother nature needs to do.
     */
    public int getSteps() {
        return steps;
    }

    /**
     * This method comunicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage[" + "nickname:" + getNickname() + ", steps:" + this.steps + "]";
    }

}
