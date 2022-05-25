package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.MissingAssistantCardException;
import it.polimi.ingsw.server.exception.NullCurrentCardException;
import it.polimi.ingsw.server.exception.ZeroCoinsException;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.Component;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private AssistantCard currentAssistantCard;
    private List<AssistantCard> hand = new ArrayList<>();
    private int coin = 0;
    private String nickname;
    private Scoreboard scoreboard;
    private boolean additionalPoints = false;

    public Player(String nickname){
        this.nickname = nickname;

        int val = 1;
        int movement = 1;
        for(int i = 0; i < 10; i++){
            hand.add(new AssistantCard(i, val, movement));
            val++;
            hand.add(new AssistantCard(i, val, movement));
            val++;
            movement++;
        }
    }

    public void createScoreboard(int numberOfPlayers, TowerColors towerColor){
        ScoreboardFactory s = new ScoreboardFactory();
        this.scoreboard = s.createScoreboard(numberOfPlayers, towerColor);
    }

    public String getNickname(){
        return this.nickname;
    }

    // public getImage()

    public AssistantCard getPlayerCard(int cardNumber){
        return hand.get(cardNumber);
    }

    public void setPlayerCards(List<Component> cards){
        int i;
        for(i=0; i<10; i++)
            hand.add((AssistantCard) cards.get(i));
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    public int getCoin(){
        return coin;
    }

    public void addCoin(){
        coin++;
    }

    public void removeCoin(int coinToRemove) {
        try {
            if (coinToRemove >= this.coin)
                this.coin = coin - coinToRemove;
            else
                throw new ZeroCoinsException("You have not enough coin!");
        } catch (ZeroCoinsException e){
            e.printStackTrace();
        }
    }

    public boolean isLastAssistantCard(){
        return hand.isEmpty();
    }


    public void setCurrentCard(AssistantCard chosenCard) {
        try {
            for(AssistantCard card: hand){
                if(card.equals(chosenCard)){
                    this.currentAssistantCard = chosenCard;
                    return;
                }
            }
            throw new MissingAssistantCardException("Assistant Card not in hand!");

        } catch (MissingAssistantCardException e) {
            throw new RuntimeException(e);
        }
    }

    public AssistantCard getCurrentCard() {
        try {
            if(currentAssistantCard == null)
                throw new NullCurrentCardException("Current " + nickname + " AssistantCard Null! ");
        } catch (NullCurrentCardException e) {
            throw new RuntimeException(e);
        }
        return currentAssistantCard;
    }

    public List<AssistantCard> getHand() {
        return hand;
    }

    /**
     * Flag that add + 2 points when calculate influence
     *
     * @param additionalPoints true or false
     */
    public void setAdditionalPoints(boolean additionalPoints) {
        this.additionalPoints = additionalPoints;
    }

    public boolean isAdditionalPoints() {
        return additionalPoints;
    }

    public void resetAssistantCard(){
        hand.remove(currentAssistantCard);
        this.currentAssistantCard = null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
