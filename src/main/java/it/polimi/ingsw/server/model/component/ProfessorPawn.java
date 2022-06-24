package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.PawnColors;

/**
 * This class identifies a component of the game map.
 * Sub-type of Pawn.
 */
public class ProfessorPawn extends Pawn{

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
