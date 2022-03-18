package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.List;

public class Player {
    private AssistantCard[] hand;
    private int coin;
    private String nickname;
    private Scoreboard scoreboard;

    public String getNickname(){
        return this.nickname;
    }

    // public getImage()

    public AssistantCard getPlayerCard(int cardNumber){
        return hand[cardNumber];
    }

    public void setPlayerCards(List<AssistantCard> cards){
        int i;
        for(i=0; i<10; i++)
            hand[i] = cards.get(i);
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    public int getNumCoin(){
        return coin;
    }
}
