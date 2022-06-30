package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card211Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = 489466202698449322L;

    int islandNumber;

    public Card211Message(String nickname, int islandNumber) {
        super(nickname, 211);
        this.islandNumber = islandNumber;
    }

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
        return "Card211Message[nickname:" + getNickname() + "island number: " + islandNumber + "]";
    }
}
