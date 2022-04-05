package it.polimi.ingsw.server.model.component;

/*
1 green
2 red
3 yellow
4 pink
5 blue

 */


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
