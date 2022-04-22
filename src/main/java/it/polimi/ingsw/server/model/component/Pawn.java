package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.PawnColors;

public abstract class Pawn extends Component {
    protected PawnColors color;

    public PawnColors getColor(){
        return color;
    }

    public int getColorInt(){
        return color.ordinal();
    }
}
