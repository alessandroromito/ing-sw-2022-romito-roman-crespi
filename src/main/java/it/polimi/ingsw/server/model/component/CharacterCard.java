package it.polimi.ingsw.server.model.component;

public class CharacterCard extends Component{
    int cost;
    boolean firstUse = false;

    public CharacterCard(int id){
        this.ID = id;
    }

    public int getCost() {
        return cost;
    }

    public void use(){
        if(!firstUse) cost++;
    }
}