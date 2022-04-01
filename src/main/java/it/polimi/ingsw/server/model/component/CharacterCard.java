package it.polimi.ingsw.server.model.component;

public class CharacterCard extends Component{
    int cost;
    boolean inUse = false;
    boolean firstUse = false;

    public int getCost() {
        return cost;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void use(){
        if(!firstUse)   cost++;
        inUse = true;
        // implementare
    }
}
