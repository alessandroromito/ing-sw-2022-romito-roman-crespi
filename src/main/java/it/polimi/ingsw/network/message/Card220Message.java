package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serial;

public class Card220Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -3212803966636937204L;

    PawnColors color;

    /**
     * Default Constructor
     * @param nickname nickname of the sender
     * @param color parameter for the effect
     */
    public Card220Message(String nickname, PawnColors color) {
        super(nickname, 220);
        this.color = color;
    }

    /**
     * @return color
     */
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
        return "Card220Message[nickname:" + getNickname() + " color: " + color + "]";
    }
}
