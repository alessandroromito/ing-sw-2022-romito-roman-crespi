package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.PawnColors;

/**
 * This class identifies a group of component of the game map.
 */
public abstract class Pawn extends Component {
    protected PawnColors color;

    /**
     * @return the color of the pawn using PawnColors enumeration.
     */
    public PawnColors getColor(){
        return color;
    }

    /**
     * @return the color of the pawn using integer variable.
     */
    public int getColorInt(){
        return color.ordinal();
    }
}
