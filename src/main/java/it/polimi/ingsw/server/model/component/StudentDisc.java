package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serializable;

public class StudentDisc extends Pawn implements Serializable {

    public StudentDisc(int id, PawnColors color){
        this.ID = id;
        this.color = color;
    }
}
