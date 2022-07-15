package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.model.player.Player;

import java.util.Comparator;

/**
 * Comparator for assistant cards.
 */
public class ComparatorAssistantCard implements Comparator<Player> {

    /**
     * Compare two assistant card.
     * @param player1 the first player to be compared.
     * @param player2 the second player to be compared.
     * @return 1 if player1's card is bigger, -1 if player2's card is bigger 0 if have the same value
     */
    public int compare(Player player1, Player player2){
        if(player1.getCurrentCard() == null)
            return -1;
        if(player2.getCurrentCard() == null)
            return 1;
        return Integer.compare(player1.getCurrentCard().getValue(), player2.getCurrentCard().getValue());
    }
}
