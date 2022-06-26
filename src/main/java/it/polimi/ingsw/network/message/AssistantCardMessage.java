package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.AssistantCard;
import java.util.List;

public class AssistantCardMessage extends  Message{
    private static final long serialVersionUID = 1001L; //da scegliere

    private final List<AssistantCard> assistantCards;
    private final List<AssistantCard> playedAssistantCards;

    public AssistantCardMessage(String nickname, List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards){
        super(nickname, MessageType.PLAY_ASSISTANT_CARD);
        this.assistantCards = assistantCards;
        this.playedAssistantCards = playedAssistantCards;
    }

    public List<AssistantCard> getAssistantCards(){
        return this.assistantCards;
    }

    public List<AssistantCard> getPlayedAssistantCards() {
        return playedAssistantCards;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "PlayAssistantCard[" + "nickname:" + getNickname() + ", assistantCards:" + this.assistantCards + "]";
    }


}
