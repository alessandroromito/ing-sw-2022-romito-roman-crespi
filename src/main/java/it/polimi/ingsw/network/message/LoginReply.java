package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class LoginReply extends Message {
    private static final long serialVersionUID = -1L; //da scegliere
    private final boolean nicknameAccepted;
    private final boolean connectionSuccessful;

    public LoginReply(boolean nicknameAccepted, boolean connectionSuccessful) {
        super(Game.SERVER_NAME, MessageType.LOGIN_REPLY);
        this.nicknameAccepted = nicknameAccepted;
        this.connectionSuccessful = connectionSuccessful;
    }

    public boolean isNicknameAccepted() {
        return nicknameAccepted;
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    @Override
    public String toString() {
        return "LoginReply[" + "nickname:" + getNickname() + ", nicknameAccepted:" + nicknameAccepted +
                ", connectionSuccessful:" + connectionSuccessful + ']';
    }
}
