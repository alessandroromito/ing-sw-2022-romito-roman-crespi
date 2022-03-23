package it.polimi.ingsw.server.model.component;

public class AssistantCard extends Component{
    private Animals animal;
    private int value;
    private int movement;

    public AssistantCard(Animals animal, int value, int movement){
        this.animal = animal;
        this.value = value;
        this.movement = movement;
    }
}
