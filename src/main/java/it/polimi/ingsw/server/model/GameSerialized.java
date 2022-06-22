package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int motherNaturePos;
    private final ArrayList<SerializableIsland> serializableIslands = new ArrayList<>();
    private final ArrayList<SerializableScoreboard> serializableScoreboard = new ArrayList<>();

    public GameSerialized(Game game){
        ArrayList<Integer> groupIDs = new ArrayList<>();

        for(Island island : game.getMap().getIslands()){
                if (island.isDisabled()) {
                    if(!groupIDs.contains(island.getGroupID())){
                        serializableIslands.add(new SerializableIsland(game.getMap().getGhostIsland(island.getID())));
                        ArrayList<Integer> referencedIslands = new ArrayList<>();
                        for (Island isl : game.getMap().getIslands()) {
                            if (isl.isDisabled() && isl.getGroupID() == island.getGroupID()) {
                                referencedIslands.add(isl.getID() + 1);
                            }
                        }
                        serializableIslands.get(serializableIslands.size() - 1).setReferencedIslands(referencedIslands);
                        groupIDs.add(island.getGroupID());
                    }
                } else {
                    serializableIslands.add(new SerializableIsland(island));
                }
        }

        for(Player player : game.getPlayers()){
            serializableScoreboard.add(new SerializableScoreboard(player.getScoreboard(), player));
        }

        this.motherNaturePos = game.getMap().getMotherNaturePosition();
    }

    public ArrayList<SerializableIsland> getSerializableIslands() {
        return serializableIslands;
    }

    public ArrayList<SerializableScoreboard> getSerializableScoreboard() {
        return serializableScoreboard;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    @Override
    public String toString() {
        return "GameSerialized:[ " + "islands: " + serializableIslands.size() + "]";
    }
}
