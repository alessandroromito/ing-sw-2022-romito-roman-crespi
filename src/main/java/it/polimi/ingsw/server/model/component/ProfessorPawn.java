package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serializable;

/**
 * This class identifies a component of the game map.
 * Subtype of Pawn.
 */
public class ProfessorPawn extends Pawn implements Serializable {

    /**
     * Default constructor.
     * @param id of the professor.
     * @param color of the professor.
     */
    public ProfessorPawn(int id, PawnColors color){
        this.ID = id;
        this.color = color;
    }

}
