package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card213Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -1647514031153802681L;
    int islandNumber;

    /**
     * Default Constructor
     * @param nickname nickname of the sender
     * @param islandNumber parameter for the effect
     */
    public Card213Message(String nickname, int islandNumber) {
        super(nickname, 213);
        this.islandNumber = islandNumber;
    }

    /**
     * @return islandNumber
     */
    public int getIslandNumber() {
        return islandNumber;
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
        return "Card213Message[nickname:" + getNickname() + "island number: " + islandNumber + "]";
    }
}
