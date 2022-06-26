package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.model.player.Player;

import java.util.Comparator;

public class ComparatorAssistantCard implements Comparator<Player> {
    public int compare(Player player1, Player player2){
        return Integer.compare(player1.getCurrentCard().getValue(), player2.getCurrentCard().getValue());
    }
}
