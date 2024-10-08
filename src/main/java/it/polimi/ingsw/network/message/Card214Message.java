package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card214Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -2809933661077260145L;

    /**
     * Default Constructor
     * @param nickname nickname of the sender
     */
    public Card214Message(String nickname) {
        super(nickname, 214);
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
        return "Card214Message[nickname:" + getNickname() + "]";
    }
}
