package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Message reply for game mode.
 */
public class GameModeReplyMessage extends Message{
    @Serial
    private static final long serialVersionUID = 1201849542872305093L;

    private final boolean expertMode;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param expertMode {code @true} if expert, {code @false} if normal
     */
    public GameModeReplyMessage(String nickname, boolean expertMode) {
        super(nickname, MessageType.GAME_MODE);
        this.expertMode = expertMode;
    }

    /**
     * @return {code @true} if expert, {code @false} if normal
     */
    public boolean getExpertMode() {
        return expertMode;
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
        return "GameModeReplyMessage[" + "nickname:" + getNickname() + ", expertMode:" + this.expertMode + "]";
    }
}
