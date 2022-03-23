package it.polimi.ingsw.server.model.component;

public class Tower extends Component {
    private int color;

    public void Tower(int color){
        this.color = color;
    }

    public int getColor(){
        return color;
    }
}
