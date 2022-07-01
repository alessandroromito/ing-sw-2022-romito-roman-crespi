package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class identifies a component of the game map.
 * Subtype of Pawn.
 */
public class StudentDisc extends Pawn implements Serializable {
    @Serial
    private static final long serialVersionUID = 47478918956462038L;

    /**
     * Default constructor.
     * @param id of the studentDisc.
     * @param color of the studentDisc.
     */
    public StudentDisc(int id, PawnColors color){
        this.ID = id;
        this.color = color;
    }
}
