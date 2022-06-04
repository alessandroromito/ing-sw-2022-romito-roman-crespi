package it.polimi.ingsw.server.model.component;

import java.io.Serializable;

public abstract class Component implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int ID;

    public int getID(){
        return this.ID;
    }

}
