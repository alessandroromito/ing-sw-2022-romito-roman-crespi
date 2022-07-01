package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.TowerColors;

/**
 * Class that create a scoreboard using the Factory method, based on the number of player chosen for the game
 */
public class ScoreboardFactory {

    /**
     * Default Constructor
     */
    public ScoreboardFactory() {
    }

    /**
     * Create the effective Scoreboard
     * @param numberOfPlayer number of player chosen
     * @param towerColor color of the tower to create
     * @param p player which belongs the scoreboard
     * @return the scoreboard created
     */
    public Scoreboard createScoreboard (int numberOfPlayer, TowerColors towerColor, Player p){
        return switch (numberOfPlayer) {
            case 2 -> new ScoreboardX2p(towerColor, p);
            case 3 -> new ScoreboardX3p(towerColor, p);
            default -> throw new IllegalArgumentException(numberOfPlayer + " number of players not supported");
        };
    }
}
