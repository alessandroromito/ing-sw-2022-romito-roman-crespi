package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card210Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = 7717501578074470181L;

    /**
     * Default constructor.
     * @param nickname nickname of the sender
     */
    public Card210Message(String nickname) {
        super(nickname, 210);
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
        return "Card210Message[nickname:" + getNickname() + "]";
    }
}
