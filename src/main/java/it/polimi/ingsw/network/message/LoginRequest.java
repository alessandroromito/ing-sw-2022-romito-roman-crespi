package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Message request for a login.
 */
public class LoginRequest extends Message {
    @Serial
    private static final long serialVersionUID = -5524844073567304815L;

    /**
     * @param nickname of the player that wants to log in the game.
     */
    public LoginRequest(String nickname) {
        super(nickname, MessageType.LOGIN_REQUEST);
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
        return "LoginRequest[" + "nickname:" + getNickname() + "]";
    }
}
