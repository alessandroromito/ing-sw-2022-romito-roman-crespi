package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;

import java.util.List;

public class UseEffectMessage extends Message{
    private static final long serialVersionUID = -1L; //da scegliere
    private final List<CharacterCard> characterCard;


    public UseEffectMessage(String nickname, List<CharacterCard> characterCard) {
        super(nickname, MessageType.USE_EFFECT);
        this.characterCard = characterCard;
    }

    public List<CharacterCard> getCharacterCard(){
        return this.characterCard;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "PlayerNumberRequest[nickname:" + getNickname() + "]";
    }
}
