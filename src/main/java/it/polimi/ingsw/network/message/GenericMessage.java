package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class GenericMessage extends Message {
    private static final long serialVersionUID = 1L; //da scegliere

    private final String message;

    public GenericMessage(String message) {
        super(Game.SERVER_NAME, MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GenericMessage[" + "nickname:" + getNickname() + ", message:" + message + "]";
    }
}
