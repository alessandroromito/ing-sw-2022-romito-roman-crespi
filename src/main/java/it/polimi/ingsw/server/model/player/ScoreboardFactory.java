package it.polimi.ingsw.server.model.player;

public class ScoreboardFactory {
    public ScoreboardFactory() {
    }

    public Scoreboard createScoreboard (int n_ofplayer, Player p){
        switch(n_ofplayer){
            case 2:
                return new ScoreboardX2p(p);
            case 3:
                return new ScoreboardX3p(p);
            default:
                throw new IllegalArgumentException(n_ofplayer + " number of players not supported");
        }
    }
}
