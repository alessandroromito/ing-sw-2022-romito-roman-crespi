package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card219Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -2709956195019031162L;
    int number;

    public Card219Message(String nickname, int number) {
        super(nickname, 219);
        this.number = number;
    }

    public int getNumber() {
        return number;
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
        return "Card219Message[nickname:" + getNickname() + " number: " + number + "]";
    }
}
