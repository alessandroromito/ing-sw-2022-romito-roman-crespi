package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.exception.NullCurrentCardException;
import it.polimi.ingsw.server.model.player.Player;

import java.util.Comparator;

class ComparatorAssistantCard implements Comparator<Player> {
    public int compare(Player player1, Player player2){
        try {

            return player1.getCurrentCard().getValue() - player2.getCurrentCard().getValue();

        } catch (NullCurrentCardException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
