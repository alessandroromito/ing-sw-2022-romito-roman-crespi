package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class PingMessage extends Message {
    private static final long serialVersionUID = 1001L; //da scegliere

    public PingMessage() {
        super(null, MessageType.PING);
    }
}
