package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.model.player.Player;

import java.util.Comparator;

/**
 * Comparator for assistant cards.
 */
public class ComparatorAssistantCard implements Comparator<Player> {

    /**
     * Object comparator.
     * @param player1 the first object to be compared.
     * @param player2 the second object to be compared.
     * @return {code @true} if are the same, {code @false} otherwise
     */
    public int compare(Player player1, Player player2){
        return Integer.compare(player1.getCurrentCard().getValue(), player2.getCurrentCard().getValue());
    }
}
