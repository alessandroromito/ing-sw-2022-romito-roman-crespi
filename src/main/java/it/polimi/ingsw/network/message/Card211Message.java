package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

public class Card211Message extends UseEffectMessage{
    private static final long serialVersionUID = 1L;
    int islandNumber;

    public Card211Message(String nickname, int islandNumber) {
        super(nickname, 211);
        this.islandNumber = islandNumber;
    }

    public int getIslandNumber() {
        return islandNumber;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card211Message[nickname:" + getNickname() + "island number: " + islandNumber + "]";
    }
}
