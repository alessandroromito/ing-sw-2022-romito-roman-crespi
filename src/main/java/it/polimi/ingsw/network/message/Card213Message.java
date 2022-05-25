package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

public class Card213Message extends UseEffectMessage{
    private static final long serialVersionUID = 1L;
    int islandNumber;

    public Card213Message(String nickname, int islandNumber) {
        super(nickname, 213);
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
        return "Card213Message[nickname:" + getNickname() + "island number: " + islandNumber + "]";
    }
}
