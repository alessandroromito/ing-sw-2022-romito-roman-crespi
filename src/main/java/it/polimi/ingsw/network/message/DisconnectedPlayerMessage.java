package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class DisconnectedPlayerMessage extends Message {
    private static final long serialVersionUID = 1001L; //da scegliere
    private final String nicknameDisconnected;
    private final String text;


    public DisconnectedPlayerMessage(String nicknameDisconnected, String text) {
        super(Game.SERVER_NAME, MessageType.DISCONNECTED_PLAYER);
        this.nicknameDisconnected = nicknameDisconnected;
        this.text = text;
    }
}
