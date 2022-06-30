package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Message that request to chose the game mode to the client.
 */
public class GameModeMessage extends Message{
    @Serial
    private static final long serialVersionUID = -1738530720638559500L;

    private final boolean expertMode;

    /**
     * @param expertMode {code @true} if expert, {code @false} if normal
     */
    public GameModeMessage(boolean expertMode) {
        super(Game.SERVER_NAME, MessageType.GAME_MODE);
        this.expertMode = expertMode;
    }

    /**
     * @return {code @true} if expert, {code @false} if normal
     */
    public boolean getExpertMode() {
        return expertMode;
    }

    /**
     * This method communicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "GameModeMessage[" + "nickname:" + getNickname() + ", expertMode:" + this.expertMode + "]";
    }
}
