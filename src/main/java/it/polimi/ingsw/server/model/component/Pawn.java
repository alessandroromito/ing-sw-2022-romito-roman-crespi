package it.polimi.ingsw.server.model.component;

/*
1 green
2 red
3 yellow
4 pink
5 blue

 */


public abstract class Pawn extends Component {
    protected int color;

    public int getColor(){
        return color;
    }
}
