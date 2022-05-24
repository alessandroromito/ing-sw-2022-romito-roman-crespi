package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

public class UseEffectMessage extends Message{
    private static final long serialVersionUID = -1L; //da scegliere
    private final int characterCardID;

    public UseEffectMessage(String nickname, int characterCardID) {
        super(nickname, MessageType.USE_EFFECT);
        this.characterCardID = characterCardID;
    }

    public int getCharacterCardID(){
        return this.characterCardID;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "UseEffectMessage[nickname:" + getNickname() + "characterCard: " + getCharacterCardID() +"]";
    }
}
