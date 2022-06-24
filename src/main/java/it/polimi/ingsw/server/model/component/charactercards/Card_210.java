package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.player.Player;

public class Card_210 extends CharacterCard {
    private Player[] old;

    public Card_210(Player[] old) {
        super(210);
        this.old = old;
        this.cost = 0; //2
    }

    public void updateOldPos (Player[] old) {
        this.old = old;
    }

    public void updateOnePos (Player old, int index){
        this.old[index] = old;
    }

    public Player getOldPos(int pos) {
        return old[pos];
    }
}
