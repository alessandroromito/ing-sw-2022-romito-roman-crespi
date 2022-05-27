package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;

public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 1L;

    private SerializableIsland[] serializableIslands = new SerializableIsland[12];
    private SerializableScoreboard[] serializableScoreboard = new SerializableScoreboard[3];

    public GameSerialized(Game game){
        int i = 0;
        for(Island island : game.getMap().getIslands()){
            serializableIslands[i] = new SerializableIsland(island);
            i++;
        }
        int j = 0;
        for(Player player : game.getPlayers()){
            serializableScoreboard[j] = new SerializableScoreboard(player.getScoreboard());
            j++;
        }
    }

    public SerializableIsland[] getSerializableIslands() {
        return serializableIslands;
    }

    public SerializableScoreboard[] getSerializableScoreboard() {
        return serializableScoreboard;
    }
}
