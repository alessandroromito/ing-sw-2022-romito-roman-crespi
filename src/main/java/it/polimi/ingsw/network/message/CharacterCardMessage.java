package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;

import java.io.Serial;
import java.util.List;

/**
 * Message that contains character cards.
 */
public class CharacterCardMessage extends  Message{
    @Serial
    private static final long serialVersionUID = -3824254042157563369L; //da scegliere

    private final List<CharacterCard> characterCards;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param characterCards character cards to be sent.
     */
    public CharacterCardMessage(String nickname, List<CharacterCard> characterCards){
        super(nickname, MessageType.PLAY_CHARACTER_CARD);
        this.characterCards = characterCards;
    }

    /**
     * @return character cards.
     */
    public List<CharacterCard> getCharacterCards(){
        return characterCards;
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
        return "CharacterCardMessage[" + "nickname:" + getNickname() + ", characterCards:" + this.characterCards + "]";
    }
}
