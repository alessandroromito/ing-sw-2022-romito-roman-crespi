package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.player.Player;

public class Card_215 extends CharacterCard {
    private Player[] old;

    public Card_215(Player[] old) {
        super(215);
        this.old = old;
        this.cost = 2;
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
