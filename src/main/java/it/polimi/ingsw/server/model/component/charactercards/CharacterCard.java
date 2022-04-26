package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.Component;

public class CharacterCard extends Component {
    protected int cost;
    private boolean firstUse = true;

    public CharacterCard(int id){
        this.ID = id;
    }

    public int getCost() {
        return cost;
    }

    public void use(){
        if(firstUse) cost++;
        firstUse = false;
    }
}