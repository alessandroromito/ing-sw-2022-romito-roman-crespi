package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Message that contains the player number chosen for a new match from the player.
 */
public class PlayerNumberReply extends Message{
    @Serial
    private static final long serialVersionUID = 1575208961918509723L; //da scegliere

    private final int playerNumber;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param playerNumber player number chosen.
     */
    public PlayerNumberReply(String nickname, int playerNumber) {
        super(nickname, MessageType.PLAYER_NUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    /**
     * @return player number chosen.
     */
    public int getPlayerNumber() {
        return playerNumber;
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
        return "PlayerNumberReply[" + "nickname:" + getNickname() + ", playerNumber:" + playerNumber + "]";
    }
}
