package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

public class Card210Message extends UseEffectMessage{
    private static final long serialVersionUID = 1002L;

    public Card210Message(String nickname) {
        super(nickname, 210);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card210Message[nickname:" + getNickname() + "]";
    }
}
