package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Abstract class message. It contains the effect to use of the character cards.
 */
public abstract class UseEffectMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8420794614118094720L;

    private final int cardID;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param cardID character card owner of the effect.
     */
    public UseEffectMessage(String nickname, int cardID) {
        super(nickname, MessageType.USE_EFFECT);
        this.cardID = cardID;
    }

    /**
     * @return character card owner of the effect.
     */
    public int getCardID() {
        return cardID;
    }

    @Override
    public String toString() {
        return "UseEffect[nickname:" + getNickname() + " CardID: " + cardID +"]";
    }
}
