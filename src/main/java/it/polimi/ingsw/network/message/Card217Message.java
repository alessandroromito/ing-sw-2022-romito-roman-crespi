package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.PawnColors;

public class Card217Message extends UseEffectMessage{
    private static final long serialVersionUID = 1L;
    PawnColors color;

    public Card217Message(String nickname, PawnColors color) {
        super(nickname, 217);
        this.color = color;
    }

    public PawnColors getColor() {
        return color;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card211Message[nickname:" + getNickname() + " color: " + color.toString() + "]";
    }
}
