package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Message request for the player number chosen for a new match from the player.
 */
public class PlayerNumberRequest extends Message {
    @Serial
    private static final long serialVersionUID = 6450164370207258637L;

    /**
     * Default constructor.
     */
    public PlayerNumberRequest() {
        super(Game.SERVER_NAME, MessageType.PLAYER_NUMBER_REQUEST);
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
        return "PlayerNumberRequest[nickname:" + getNickname() + "]";
    }
}
