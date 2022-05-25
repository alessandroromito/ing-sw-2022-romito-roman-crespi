package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

public class Card214Message extends UseEffectMessage{
    private static final long serialVersionUID = 1002L;

    public Card214Message(String nickname) {
        super(nickname, 214);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card214Message[nickname:" + getNickname() + "]";
    }
}
