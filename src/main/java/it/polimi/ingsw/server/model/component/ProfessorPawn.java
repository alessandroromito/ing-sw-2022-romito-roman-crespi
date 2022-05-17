package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.PawnColors;

public class ProfessorPawn extends Pawn{

    public ProfessorPawn(int id, PawnColors color){
        this.ID = id;
        this.color = color;
        this.positionOnMap = MapPositions.NA;
    }

}
