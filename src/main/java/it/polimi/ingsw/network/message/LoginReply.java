package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Reply of the login request message.
 */
public class LoginReply extends Message {
    @Serial
    private static final long serialVersionUID = -4856859526126957938L;

    private final boolean nicknameAccepted;
    private final boolean connectionSuccessful;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param nicknameAccepted {code @true} if nickname accepted, {code @false} otherwise.
     * @param connectionSuccessful {code @true} if connection established, {code @false} otherwise.
     */
    public LoginReply(String nickname, boolean nicknameAccepted, boolean connectionSuccessful) {
        super(nickname, MessageType.LOGIN_REPLY);
        this.nicknameAccepted = nicknameAccepted;
        this.connectionSuccessful = connectionSuccessful;
    }

    /**
     * @return {code @true} if nickname accepted, {code @false} otherwise.
     */
    public boolean isNicknameAccepted() {
        return nicknameAccepted;
    }

    /**
     * @return {code @true} if connection established, {code @false} otherwise.
     */
    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
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
        return "LoginReply[" + "nickname:" + getNickname() + ", nicknameAccepted:" + nicknameAccepted +
                ", connectionSuccessful:" + connectionSuccessful + ']';
    }
}
