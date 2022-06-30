package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

/**
 * Message reply that contains character cards.
 */
public class CharacterCardReplyMessage extends  Message{
    @Serial
    private static final long serialVersionUID = 3988216677158483297L;

    boolean useEffect;

    /**
     * Constructor.
     * @param nickname nickname of the owner of the message.
     * @param useEffect effect to be used.
     */
    public CharacterCardReplyMessage(String nickname, boolean useEffect){
        super(nickname, MessageType.PLAY_CHARACTER_CARD);
        this.useEffect = useEffect;
    }

    public boolean getUseEffect() {
        return useEffect;
    }

    /**
     * This method comunicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "CharacterCardReplyMessage[" + "nickname:" + getNickname() + ", useEffect:" + useEffect + "]";
    }
}
