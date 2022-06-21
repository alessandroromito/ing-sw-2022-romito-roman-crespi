package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.Component;

public class CharacterCard extends Component {
    protected int cost;
    protected boolean firstUse = true;

    public CharacterCard(int id){
        this.ID = id;
    }

    public int getCost() {
        return this.cost;
    }

    public void addCost(){
        if(firstUse){
            cost++;
            firstUse = false;
        }
    }
}