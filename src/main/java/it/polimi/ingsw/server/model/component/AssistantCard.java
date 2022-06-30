package it.polimi.ingsw.server.model.component;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class identifies a complex component of the game map.
 */
public class AssistantCard extends Component implements Serializable {
    @Serial
    private static final long serialVersionUID = 6062936035595096057L;

    private Animals animal;
    private final int value;
    private final int movement;

    /**
     * Default constructor.
     * @param id of the assistant card.
     * @param value of the assistant card.
     * @param movement of the assistant card.
     */
    public AssistantCard(int id, int value, int movement){
        this.ID = id;
        this.value = value;
        this.movement = movement;
    }

    /**
     * Set the type of animal of the assistant card.
     * @param animal
     */
    public void setAnimal(Animals animal) {
        this.animal = animal;
    }

    /**
     * Get the type of animal of the assistant card.
     */
    public Animals getAnimal(){
        return this.animal;
    }

    /**
     * Get the value of the assistant card.
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Get the maximum movement allowed from the assistant card.
     */
    public int getMovement(){
        return this.movement;
    }
}
