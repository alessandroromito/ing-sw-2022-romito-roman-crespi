package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.exception.MissingAssistantCardException;
import it.polimi.ingsw.server.exception.NullCurrentCardException;
import it.polimi.ingsw.server.exception.ZeroCoinsException;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.Component;

import java.util.List;

public class Player {

    private AssistantCard currentAssistantCard;
    private List<AssistantCard> hand;
    private int coin = 0;
    private String nickname;
    private Scoreboard scoreboard;
    private GameController controller;
    private boolean additionalPoints = false;

    public Player(String nickname){
        this.nickname = nickname;
    }

        public void createScoreboard(int nofplayers,Player p){
        ScoreboardFactory s = new ScoreboardFactory();
        this.scoreboard = s.createScoreboard(nofplayers,p);
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

    public void removeCoin() throws ZeroCoinsException {
        if(coin>0)
            coin--;
        else throw new ZeroCoinsException("No coins remaining");
    }

    public boolean isLastAssistantCard(){
        return hand.isEmpty();
    }

    public void notifyUsingAssistantCard(){
        if(isLastAssistantCard())
            controller.lastTurn();
    }

    public void setCurrentCard(AssistantCard chosenCard) throws MissingAssistantCardException {
        for(AssistantCard card: hand){
            if(card.equals(chosenCard)){
                hand.remove(card);
                this.currentAssistantCard = chosenCard;
                currentAssistantCard.setPosition(MapPositions.TRASH);
                notifyUsingAssistantCard();
                return;
            }
        } throw new MissingAssistantCardException("Assistant Card not in hand!");

    }

    public AssistantCard getCurrentCard() throws NullCurrentCardException {
        if(currentAssistantCard.equals(null)) throw new NullCurrentCardException("Current AssistantCard Null!");
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

}
