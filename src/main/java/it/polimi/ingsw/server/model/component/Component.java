package it.polimi.ingsw.server.model.component;

import java.io.Serializable;

/**
 * This class identifies the father class of all the components.
 */
public abstract class Component implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int ID;

    /**
     * @return the id of the component.
     */
    public int getID(){
        return this.ID;
    }

}
