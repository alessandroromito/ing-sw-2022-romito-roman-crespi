package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card214Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -2809933661077260145L;

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
