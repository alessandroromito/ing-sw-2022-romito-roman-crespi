package it.polimi.ingsw.server.model.player;

public class ScoreboardFactory {
    public Scoreboard ScoreboardCreator(int N_ofplayer){
        switch(N_ofplayer){
            case 2:
                return new ScoreboardX2p();
            case 3:
                return new ScoreboardX3p();
            default:
                throw IllegalArgumentException(N_ofplayer": wrong number of players");
        }
    }
}
