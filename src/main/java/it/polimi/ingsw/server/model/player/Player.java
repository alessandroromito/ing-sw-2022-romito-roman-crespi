package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.exception.NullCurrentCardException;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.observer.ObserverLastAssistentCard;

import java.util.List;

public class Player {
    private AssistantCard currentAssistantCard;
    private List<AssistantCard> hand;
    private int coin = 0;
    private String nickname;
    private Scoreboard scoreboard;
    private final ObserverLastAssistentCard obsLAC;

    public Player(){
        // da implementare la mano
        obsLAC = new ObserverLastAssistentCard(this);
    }

    public String getNickname(){
        return this.nickname;
    }

    // public getImage()

    public AssistantCard getPlayerCard(int cardNumber){
        return hand.get(cardNumber);
    }

    public void setPlayerCards(List<AssistantCard> cards){
        int i;
        for(i=0; i<10; i++)
            hand.add(cards.get(i));
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    public int getNumCoin(){
        return coin;
    }

    public void addCoin(){coin++;}

    public boolean isLastAssistantCard(){
        return hand.isEmpty();
    }

    public void notifyUsingAssistantCard(){
        obsLAC.onUpdate();
    }

    public void setCurrentCard(AssistantCard chosenCard) {
        for(AssistantCard card: hand){
            if(card.equals(chosenCard)){
                hand.remove(card);
                this.currentAssistantCard = chosenCard;
            }
        }

    }

    public AssistantCard getCurrentCard() throws NullCurrentCardException {
        if(currentAssistantCard.equals(null)) throw new NullCurrentCardException("Current AssistantCard Null!");
        return currentAssistantCard;
    }
}
