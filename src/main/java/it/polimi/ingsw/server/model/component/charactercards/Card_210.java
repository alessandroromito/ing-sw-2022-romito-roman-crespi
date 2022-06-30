package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.player.Player;
/**
 * This class represent the character card with id 210.
 */
public class Card_210 extends CharacterCard {
    private Player[] old;

    /**
     * Default Constructor
     * @param old players which belong the professors
     */
    public Card_210(Player[] old) {
        super(210);
        this.old = old;
        this.cost = 2;
    }

    /**
     * Setter for array old
     * @param old new array to be set
     */
    public void updateOldPos (Player[] old) {
        this.old = old;
    }

    /**
     * Setter for one pos of the array Player
     * @param old new Player
     * @param index index
     */
    public void updateOnePos (Player old, int index){
        this.old[index] = old;
    }

    /**
     * Getter
     * @param pos index
     * @return
     */
    public Player getOldPos(int pos) {
        return old[pos];
    }
}
