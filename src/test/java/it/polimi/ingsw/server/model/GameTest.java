package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.DifferentColorTowerException;
import it.polimi.ingsw.server.exception.FullGroupIDListException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.MotherNature;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.Tower;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private List<String> players = new ArrayList<>();
    protected ArrayList<Component> components;

    @Before
    public void setUp() {
        players.add("player1");
        players.add("player2");
        players.add("player3");

        game = new Game(players);
        components = game.components;

    }
/*
    @Test
    public void scoreboardX3p(){
        Object s2 = players.get(1).getScoreboard();
        assertTrue(s2 instanceof ScoreboardX3p);
    }
*/
    @Test
    public void CreateComponents(){

        // Print a visual log of the component's ID and class type
        for(int i = 0; i<188; i++) {
            System.out.print("ID: " + components.get(i).getID() + " ");
            System.out.println(components.get(i).getClass().getName());
        }

        assertNotNull(components.get(0));
        assertEquals(MotherNature.class, components.get(0).getClass());

        assertNotNull(components.get(2));
        assertEquals(ProfessorPawn.class, components.get(2).getClass());
        ProfessorPawn p = (ProfessorPawn) components.get(2);
        assertEquals(PawnColors.BLUE, p.getColor());

        assertNotNull(components.get(5));
        assertEquals(ProfessorPawn.class, components.get(5).getClass());
        p = (ProfessorPawn) components.get(5);
        assertEquals(PawnColors.PINK, p.getColor());

        assertNotNull(components.get(7));
        assertEquals(Tower.class, components.get(7).getClass());
        Tower t = (Tower) components.get(7);
        assertEquals(TowerColors.BLACK, t.getColor());

    }

    @Test
    public void GameInitialization(){
        Map map = game.getMap();

        assertEquals(map.getMotherNaturePosition(), components.get(0).getPosition().ordinal() - 12);
        components.get(0).setPosition(MapPositions.valueOf("ISLAND_" + 1));
        assertEquals(0, components.get(0).getPosition().ordinal() - 12);

        // Opposite island free
        Island oppositeIsland = map.getIsland(map.getMotherNaturePosition());
        assertArrayEquals(oppositeIsland.getNumberOfColors(), null);

        // Scoreboard entrance is full
        Player p1 = null;
        p1 = game.getPlayerByNickname("Player 1");
        Scoreboard scoreboard1 = p1.getScoreboard();

        Player p2 = game.getPlayerByNickname("Player 2");
        Scoreboard scoreboard2 = p1.getScoreboard();

        assertEquals(7, scoreboard1.getNumStudentsFromEntrance());
        assertEquals(7, scoreboard2.getNumStudentsFromEntrance());

    }

    @Test
    public void moveMotherNatureTest() throws DifferentColorTowerException, FullGroupIDListException {
        Map map = game.getMap();
        map.setMotherNaturePos(2);

        game.moveMotherNature(4);

        assertEquals(6, map.getMotherNaturePosition());
        assertEquals(MapPositions.ISLAND_6, game.getComponent(1).getPosition());

        map.merge(1,2);

    }

    @Test
    public void getPlayerByNicknameTest() throws MissingPlayerNicknameException {
        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        players.add(player1);
        players.add(player2);
        players.add(player3);

        this.game = new Game(players);

        assertEquals(players.get(0), game.getPlayerByNickname("Player 1"));
        assertEquals(players.get(1), game.getPlayerByNickname("Player 2"));
        assertEquals(players.get(2), game.getPlayerByNickname("Player 3"));

    }


    @Test
    public void getPlayersNicknames() {
    }

    @Test
    public void refillClouds() {
    }

    @Test
    public void setAssistantCard() {
    }

    @Test
    public void moveStudentToIsland() {
    }

    @Test
    public void moveTowerToIsland() {
    }

    @Test
    public void moveProfessor() {
    }

    @Test
    public void moveStudentToDiningRoom() {
    }

    @Test
    public void deleteActiveCard() {
    }

    @Test
    public void pickAndPlaceFromCloud() {
    }

    @Test
    public void getComponentByIDTest() {
        assertEquals(components.get(0), game.getComponent(1));
        assertEquals(components.get(1), game.getComponent(2));
    }
}