package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.TowerColors;

public class ScoreboardFactory {
    public ScoreboardFactory() {
    }

    public Scoreboard createScoreboard (int numberOfPlayer, TowerColors towerColor){
        switch(numberOfPlayer){
            case 2:
                return new ScoreboardX2p(towerColor);
            case 3:
                return new ScoreboardX3p(towerColor);
            default:
                throw new IllegalArgumentException(numberOfPlayer + " number of players not supported");
        }
    }
}
