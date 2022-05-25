package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

public class Card212Message extends UseEffectMessage{
    private static final long serialVersionUID = 1L;

    public Card212Message(String nickname) {
        super(nickname, 212);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card212Message[nickname:" + getNickname() + "]";
    }
}
