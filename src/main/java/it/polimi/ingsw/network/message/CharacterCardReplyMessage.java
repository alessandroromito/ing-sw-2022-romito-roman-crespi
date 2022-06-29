package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;

public class CharacterCardReplyMessage extends  Message{
    @Serial
    private static final long serialVersionUID = 3988216677158483297L;

    boolean useEffect;

    public CharacterCardReplyMessage(String nickname, boolean useEffect){
        super(nickname, MessageType.PLAY_CHARACTER_CARD);
        this.useEffect = useEffect;
    }

    public boolean getUseEffect() {
        return useEffect;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "CharacterCardReplyMessage[" + "nickname:" + getNickname() + ", useEffect:" + useEffect + "]";
    }
}
