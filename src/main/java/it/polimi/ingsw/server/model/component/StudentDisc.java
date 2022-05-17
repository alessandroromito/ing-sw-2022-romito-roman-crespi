package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.PawnColors;

public class StudentDisc extends Pawn{

    public StudentDisc(int id, PawnColors color){
        this.ID = id;
        this.color = color;
        this.positionOnMap = MapPositions.BAG;
    }
}
