package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSerializedTest {

    private int motherNaturePos;
    private final ArrayList<SerializableIsland> serializableIslands = new ArrayList<>();
    private final ArrayList<SerializableScoreboard> serializableScoreboard = new ArrayList<>();
    private Game game;
    private GameSerialized gameSerialized;

    @BeforeEach
    void setUp() {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("Giorgio");
        nicknames.add("Marco");
        game = new Game(nicknames);
        gameSerialized = new GameSerialized(game);

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

        this.motherNaturePos = 10;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSerializableIslands() {
        assertEquals(SerializableIsland.class, gameSerialized.getSerializableIslands().get(0).getClass());
    }

    @Test
    void getSerializableScoreboard() {
        assertEquals(SerializableScoreboard.class, gameSerialized.getSerializableScoreboard().get(0).getClass());
    }

    @Test
    void getMotherNaturePos() {
        assertTrue(gameSerialized.getMotherNaturePos() >= 0);
        assertTrue(gameSerialized.getMotherNaturePos() <= 11);
    }

    @Test
    void testToString() {
    }
}