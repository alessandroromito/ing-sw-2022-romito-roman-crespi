package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.MissingAssistantCardException;
import it.polimi.ingsw.server.model.component.AssistantCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a player of the game, every player has a scoreboard, and it's observable
 */
public class Player extends Observable implements Serializable {
    @Serial
    private static final long serialVersionUID = -8025605356035173260L;

    private AssistantCard currentAssistantCard;
    private final List<AssistantCard> hand = new ArrayList<>();
    private int coin = 0;

    private String nickname;
    private boolean connected;

    private Scoreboard scoreboard;
    private boolean additionalPoints = false;

    /**
     * Default Constructor
     * @param nickname nickname of the player
     */
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

    /**
     * Initialize a new scoreboard
     * @param numberOfPlayers used to generate the correct scoreboard
     * @param towerColor color of the tower on the generated scoreboard
     */
    public void createScoreboard(int numberOfPlayers, TowerColors towerColor){
        ScoreboardFactory s = new ScoreboardFactory();
        this.scoreboard = s.createScoreboard(numberOfPlayers, towerColor,this);
    }

    /**
     * Getter
     * @return the player's scoreboard
     */
    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    /**
     * Getter
     * @param cardNumber index
     * @return the Assistant Card at that index
     */
    public AssistantCard getPlayerCard(int cardNumber){
        for(AssistantCard assistantCard : hand){
            if(assistantCard.getID() == cardNumber)
                return assistantCard;
        }
        return null;
    }

    /**
     * Getter
     * @return the current coin
     */
    public int getCoin(){
        return coin;
    }

    /**
     * USed to add a coin
     */
    public void addCoin(){
        coin++;
    }

    /**
     * Used to remove a certain quantity of coin
     * @param coinToRemove number of coin to remove
     */
    public void removeCoin(int coinToRemove) {
        this.coin = coin - coinToRemove;
    }

    /**
     * Setter for the assistant card
     * @param chosenCard set this as the current card
     */
    public boolean setCurrentCard(AssistantCard chosenCard) {
        try {
            for(AssistantCard card: hand){
                if(card.getValue() == chosenCard.getValue()){
                    this.currentAssistantCard = card;
                    return true;
                }
            }
            throw new MissingAssistantCardException("Assistant Card not in hand!");
        } catch (MissingAssistantCardException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter
     * @return current Assistant Card
     */
    public AssistantCard getCurrentCard() {
        return currentAssistantCard == null ? null : currentAssistantCard;
    }

    /**
     * Getter
     * @return the available Assistant Cards
     */
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

    /**
     * Check if the effect of the additional point on influence is enabled
     * @return true if the effect is enabled
     */
    public boolean isAdditionalPoints() {
        return additionalPoints;
    }

    /**
     * Reset the current Assistant Card
     */
    public void resetAssistantCard(){
        hand.remove(currentAssistantCard);
        this.currentAssistantCard = null;
    }

    /**
     * Getter
     * @return nickname of this player
     */
    public String getNickname(){
        return this.nickname;
    }

    /**
     * Setter of the nickname
     * @param nickname nick to be set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Check if is connected
     * @return true if it's connected, false otherwise
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Setter
     * @param connected boolean true if it's online
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String toString(){
        return this.nickname;
    }
}
