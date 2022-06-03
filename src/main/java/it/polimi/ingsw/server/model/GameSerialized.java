package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ArrayList<SerializableIsland> serializableIslands = new ArrayList<>();
    private final ArrayList<SerializableScoreboard> serializableScoreboard = new ArrayList<>();

    public GameSerialized(Game game){

        for(Island island : game.getMap().getIslands()){
            if(island.isDisabled()) {
                serializableIslands.add(new SerializableIsland(game.getMap().getGhostIsland(island.getID())));
                ArrayList<Integer> referencedIslands = new ArrayList<>();
                for(Island isl : game.getMap().getIslands()){
                    if(isl.isDisabled()){
                        referencedIslands.add(isl.getGroupID());
                    }
                }
                serializableIslands.get(serializableIslands.size()).setReferencedIslands(referencedIslands);
            }
            else{
                serializableIslands.add(new SerializableIsland(island));
            }
        }
        for(Player player : game.getPlayers()){
            serializableScoreboard.add(new SerializableScoreboard(player.getScoreboard(), player.getNickname()));
        }
    }

    public ArrayList<SerializableIsland> getSerializableIslands() {
        return serializableIslands;
    }

    public ArrayList<SerializableScoreboard> getSerializableScoreboard() {
        return serializableScoreboard;
    }
}
