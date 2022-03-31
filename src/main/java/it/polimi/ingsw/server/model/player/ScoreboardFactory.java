package it.polimi.ingsw.server.model.player;

public class ScoreboardFactory {
    public Scoreboard CreateScoreboard (int N_ofplayer){
        switch(N_ofplayer){
            case 2:
                return new ScoreboardX2p();
            case 3:
                return new ScoreboardX3p();
            default:
                throw new IllegalArgumentException(N_ofplayer + " number of players not supported");
        }
    }
}
