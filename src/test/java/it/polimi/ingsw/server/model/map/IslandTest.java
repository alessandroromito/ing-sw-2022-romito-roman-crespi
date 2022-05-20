package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.AddingWrongColorTowerToIslandException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IslandTest {

    Island island;
    private Game game;
    private List<String> players = new ArrayList<>();

    @BeforeEach
    void setUp() {
        island = new Island(1);
        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        players.add(player1);
        players.add(player2);
        players.add(player3);

        game = new Game(players);    }

    @AfterEach
    void tearDown() {
        island = null;
    }

    @Test
    void getNumberOfColors() {
    }

    @Test
    void getID() {
        assertEquals(1, island.getID());
    }

    @Test
    void getGroupID() {
    }

    @Test
    void setGroupID() {
        island.setGroupID(2);
        assertEquals(island.getGroupID(), 2);
    }

    //ci sono altri casi
    @Test
    void getInfluence() throws AddingWrongColorTowerToIslandException {
        Player player1 = null;
        player1 = game.getPlayerByNickname("Player 1");
        player1.getScoreboard().setProfessorTrue(PawnColors.RED);

        island = game.getMap().getIsland(1);

        island.addTower(player1.getScoreboard().getTowerColor());
        island.addStudent(PawnColors.RED);
        island.addStudent(PawnColors.BLUE);
        island.addStudent(PawnColors.GREEN);
        island.addStudent(PawnColors.RED);
        island.addStudent(PawnColors.PINK);

        assertEquals(3, island.getInfluence(player1));
    }

    @Test
    void getInfluence_219() {
    }

    @Test
    void getInfluence_222() {
    }

    @Test
    void getTowerNumber() {

    }

    @Test
    void disable() {
    }

    @Test
    void isDisabled() {
    }

    @Test
    void addStudent() {
    }

    @Test
    void addTower() {
    }

    @Test
    void getTowerColor() {
    }

    @Test
    void switchTowerColor() {
    }

    @Test
    void addNoEntryTile() {
    }

    @Test
    void checkNoEntryTile() {
    }

    @Test
    void removeNoEntryTile() {
    }
}