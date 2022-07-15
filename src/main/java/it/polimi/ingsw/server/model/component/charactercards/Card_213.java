package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.exception.ZeroNoEntryTileRemainingException;
import it.polimi.ingsw.server.model.component.NoEntryTile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the character card with id 213.
 */
public class Card_213 extends CharacterCard{
    ArrayList<NoEntryTile> noEntryTiles = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Card_213(List<NoEntryTile> nt) {
        super(213);
        this.cost = 2; //2

        for(int i=0;i<4;i++)
            this.noEntryTiles.add(nt.get(i));
    }

    /**
     * Method to use this card
     * @return a no entry tile
     */
    public NoEntryTile use() {
        try {
            if(!noEntryTiles.isEmpty())
                return noEntryTiles.remove(0);
            else throw new ZeroNoEntryTileRemainingException("No entry tiles available");
        }catch (ZeroNoEntryTileRemainingException e){
            System.out.println("No entry tiles remaining");
        }
        return null;
    }

    /**
     * Add a no entry tile to this card
     * @param noEntryTile tile to add
     */
    public void recoverTile(NoEntryTile noEntryTile){
        noEntryTiles.add(noEntryTile);
    }
}
