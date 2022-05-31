package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

public class LoginReply extends Message {
    private static final long serialVersionUID = -1L; //da scegliere
    private final boolean nicknameAccepted;
    private final boolean connectionSuccessful;

    public LoginReply(String nickname, boolean nicknameAccepted, boolean connectionSuccessful) {
        super(nickname, MessageType.LOGIN_REPLY);
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
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "LoginReply[" + "nickname:" + getNickname() + ", nicknameAccepted:" + nicknameAccepted +
                ", connectionSuccessful:" + connectionSuccessful + ']';
    }
}
