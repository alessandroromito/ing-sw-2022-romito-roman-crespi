package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

public class Card219Message extends UseEffectMessage{
    private static final long serialVersionUID = 1L;
    int number;

    public Card219Message(String nickname, int number) {
        super(nickname, 219);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card211Message[nickname:" + getNickname() + " number: " + number + "]";
    }
}
