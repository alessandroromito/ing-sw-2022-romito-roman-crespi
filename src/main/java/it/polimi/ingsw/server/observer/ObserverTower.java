package it.polimi.ingsw.server.observer;
import it.polimi.ingsw.server.model.Model;
//for each player... Inizialize in gameInizialization()

import it.polimi.ingsw.server.model.player.Scoreboard;

public class ObserverTower extends MainObserver{
    Scoreboard scoreboard;

    public ObserverTower(Scoreboard scoreboard){
            this.scoreboard = scoreboard;
    }

    @Override
    public void onUpdate() {
        if(scoreboard.getNumTowers() == 0)
            endGamePhase("LAST_TOWER");
    }
}
