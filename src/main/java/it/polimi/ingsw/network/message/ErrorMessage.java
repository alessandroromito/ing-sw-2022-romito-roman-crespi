package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class ErrorMessage extends Message {
    private static final long serialVersionUID = 1002L;
    private final String error;

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage[nickname:" + getNickname() + ", error:" + error + "]";
    }
}
