package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serial;

public class Card217Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = 2424014591728955869L;

    PawnColors color;

    public Card217Message(String nickname, PawnColors color) {
        super(nickname, 217);
        this.color = color;
    }

    public PawnColors getColor() {
        return color;
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
        return "Card217Message[nickname:" + getNickname() + " color: " + color.toString() + "]";
    }
}
