package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.Animals;

public class AssistantCard extends Component{
    private Animals animal;
    private int value;
    private int movement;

    public AssistantCard(int id, int value, int movement){
        this.ID = id;
        this.value = value;
        this.movement = movement;
    }

    public void setAnimal(Animals animal) {
        this.animal = animal;
    }

    public Animals getAnimal(){
        return this.animal;
    }

    public int getValue(){
        return this.value;
    }

    public int getMovement(){
        return this.movement;
    }
}
