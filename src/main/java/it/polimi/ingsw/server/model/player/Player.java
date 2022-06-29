package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.MissingAssistantCardException;
import it.polimi.ingsw.server.model.component.AssistantCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player extends Observable implements Serializable {

    private AssistantCard currentAssistantCard;
    private final List<AssistantCard> hand = new ArrayList<>();
    private int coin = 0;

    private String nickname;
    private boolean connected;

    private Scoreboard scoreboard;
    private boolean additionalPoints = false;

    public Player(String nickname){
        this.nickname = nickname;
        this.connected = true;

        int val = 1;
        int movement = 1;
        for(int i = 1; i <= 10; i++){
            hand.add(new AssistantCard(i, val, movement));
            val++;
            i++;
            hand.add(new AssistantCard(i, val, movement));
            val++;
            movement++;
        }
    }

    public void createScoreboard(int numberOfPlayers, TowerColors towerColor){
        ScoreboardFactory s = new ScoreboardFactory();
        this.scoreboard = s.createScoreboard(numberOfPlayers, towerColor,this);
    }

    public String getNickname(){
        return this.nickname;
    }

    public AssistantCard getPlayerCard(int cardNumber){
        for(AssistantCard assistantCard : hand){
            if(assistantCard.getID() == cardNumber)
                return assistantCard;
        }
        return null;
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
        this.coin = coin - coinToRemove;
    }

    public void setCurrentCard(AssistantCard chosenCard) {
        try {
            for(AssistantCard card: hand){
                if(card.getValue() == chosenCard.getValue()){
                    this.currentAssistantCard = card;
                    return;
                }
            }
            throw new MissingAssistantCardException("Assistant Card not in hand!");

        } catch (MissingAssistantCardException e) {
            throw new RuntimeException(e);
        }
    }

    public AssistantCard getCurrentCard() {
        return currentAssistantCard == null ? null : currentAssistantCard;
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

    @Override
    public String toString(){
        return this.nickname;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
