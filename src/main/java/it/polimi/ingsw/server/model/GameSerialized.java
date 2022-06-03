package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 1L;

    private final SerializableIsland[] serializableIslands = new SerializableIsland[12];
    private final SerializableScoreboard[] serializableScoreboard = new SerializableScoreboard[0];

    public GameSerialized(Game game){
        int i = 0;
        for(Island island : game.getMap().getIslands()){
            if(island.isDisabled()) {
                serializableIslands[i] = new SerializableIsland(game.getMap().getGhostIsland(island.getID()));
                ArrayList<Integer> referencedIslands = new ArrayList<>();
                for(Island isl : game.getMap().getIslands()){
                    if(isl.isDisabled()){
                        referencedIslands.add(isl.getGroupID());
                    }
                }
                serializableIslands[i].setReferencedIslands(referencedIslands);
            }
            else{
                serializableIslands[i] = new SerializableIsland(island);
            }
            i++;
        }
        int j = 0;
        for(Player player : game.getPlayers()){
            serializableScoreboard[j] = new SerializableScoreboard(player.getScoreboard(), player.getNickname());
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
