package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.AssistantCard;

import java.io.Serial;
import java.util.List;

/**
 * Message that contains assistant cards.
 */
public class AssistantCardMessage extends  Message{

    @Serial
    private static final long serialVersionUID = 17189246329856329L;

    private final List<AssistantCard> assistantCards;
    private final List<AssistantCard> playedAssistantCards;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param assistantCards assistant cards to be sent.
     * @param playedAssistantCards played assistant cards to be sent.
     */
    public AssistantCardMessage(String nickname, List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards){
        super(nickname, MessageType.PLAY_ASSISTANT_CARD);
        this.assistantCards = assistantCards;
        this.playedAssistantCards = playedAssistantCards;
    }

    /**
     * @return assistant cards.
     */
    public List<AssistantCard> getAssistantCards(){
        return this.assistantCards;
    }

    /**
     * @return played assistant cards.
     */
    public List<AssistantCard> getPlayedAssistantCards() {
        return playedAssistantCards;
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
        return "PlayAssistantCard[" + "nickname:" + getNickname() + ", assistantCards:" + this.assistantCards + "]";
    }


}
