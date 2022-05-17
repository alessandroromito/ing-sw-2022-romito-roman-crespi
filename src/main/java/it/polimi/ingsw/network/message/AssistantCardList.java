package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.AssistantCard;
import java.util.List;

public class AssistantCardList extends  Message{
    private static final long serialVersionUID = 1001L; //da scegliere

    private final List<AssistantCard> assistantCards;

    public AssistantCardList(String nickname, List<AssistantCard> assistantCards){
        super(nickname, MessageType.PLAY_ASSISTANT_CARD);
        this.assistantCards = assistantCards;
    }

    public List<AssistantCard> getCardNumber(){
        return this.assistantCards;
    }

    @Override
    public String toString() {
        return "PlayAssistantCard[" + "nickname:" + getNickname() + ", assistantCards:" + this.assistantCards + "]";
    }
}
