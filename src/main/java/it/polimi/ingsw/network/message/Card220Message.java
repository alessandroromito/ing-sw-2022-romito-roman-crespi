package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serial;

public class Card220Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -3212803966636937204L;

    PawnColors color;

    public Card220Message(String nickname, PawnColors color) {
        super(nickname, 220);
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
        return "Card220Message[nickname:" + getNickname() + " color: " + color + "]";
    }
}
