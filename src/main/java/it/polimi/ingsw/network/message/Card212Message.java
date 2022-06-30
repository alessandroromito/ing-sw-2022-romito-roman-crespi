package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card212Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -896254201388907635L;

    /**
     * Default Constructor
     * @param nickname nickname of the sender
     */
    public Card212Message(String nickname) {
        super(nickname, 212);
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
        return "Card212Message[nickname:" + getNickname() + "]";
    }
}
