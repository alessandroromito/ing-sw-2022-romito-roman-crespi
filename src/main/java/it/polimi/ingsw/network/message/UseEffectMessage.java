package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public abstract class UseEffectMessage extends Message {
    private static final long serialVersionUID = 1L; //da scegliere
    private final int cardID;

    public UseEffectMessage(String nickname, int cardID) {
        super(nickname, MessageType.USE_EFFECT);
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }

    @Override
    public String toString() {
        return "UseEffect[nickname:" + getNickname() + " CardID: " + cardID +"]";
    }
}
