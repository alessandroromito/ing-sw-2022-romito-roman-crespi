package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.Component;

/**
 * General class for all the CharacterCards
 */
public class CharacterCard extends Component {
    protected int cost;
    protected boolean firstUse = true;

    /**
     * Default Constructor
     */
    public CharacterCard(int id){
        this.ID = id;
    }

    /**
     * @return the cost of the card
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Add +1 to the cost
     */
    public void addCost(){
        if(firstUse){
            cost++;
            firstUse = false;
        }
    }
}