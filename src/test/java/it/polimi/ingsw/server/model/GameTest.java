package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.DifferentColorTowerException;
import it.polimi.ingsw.server.exception.FullGroupIDListException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.MotherNature;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.map.Cloud;
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
        components = game.getComponents();

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

        game.createComponents();
        components = game.getComponents();

        // Print a visual log of the component's ID and class type
        //for(int i = 0; i<188; i++) {
//            System.out.print("ID: " + components.get(i).getID() + " ");
//            System.out.println(components.get(i).getClass().getName());
//        }

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

    }

    @Test
    public void GameInitialization(){
        Map map = game.getMap();

        for(Integer integer : map.getIsland(map.getMotherNaturePosition()).getNumberOfColors())
        {
            System.out.println(integer);
            assertEquals(0, (int) integer);
        }

        // Opposite island free
        int oppositeIslandNum = map.getIsland(game.oppositePosition()).getID();

        //Island oppositeIsland = map.getIsland(map.getMotherNaturePosition());
        for(Integer integer : map.getIsland(oppositeIslandNum).getNumberOfColors())
        {
            System.out.println(integer);
            assertEquals(0, (int) integer);
        }
        //assertArrayEquals(oppositeIsland.getNumberOfColors(), null);

        // Scoreboard entrance is full
        Player p1 = null;
        p1 = game.getPlayerByNickname("player1");
        Scoreboard scoreboard1 = p1.getScoreboard();

        Player p2 = game.getPlayerByNickname("player2");
        Scoreboard scoreboard2 = p1.getScoreboard();

        assertEquals(9, scoreboard1.getNumStudentsFromEntrance());
        assertEquals(9, scoreboard2.getNumStudentsFromEntrance());
        assertEquals(9, scoreboard1.getNumStudentsFromEntrance());

    }

    @Test
    public void moveMotherNatureTest() throws DifferentColorTowerException, FullGroupIDListException {
        /*
        GameController gameController = new GameController();
        //Game game = gameController.getGame();
        Map map = game.getMap();

        map.setMotherNaturePos(2);

        game.moveMotherNature(4);

        assertEquals(6, map.getMotherNaturePosition());

        map.merge(1,2);

         */

    }

    @Test
    public void getPlayerByNicknameTest() throws MissingPlayerNicknameException {

        Game game;
        //già aggiunti
        /*
        String player1 = "Player1";
        String player2 = "Player2";
        String player3 = "Player3";

        players.add(player1);
        players.add(player2);
        players.add(player3);
         */

        game = new Game(players);

        assertEquals(players.get(0), game.getPlayerByNickname("player1").getNickname());
        assertEquals(players.get(1), game.getPlayerByNickname("player2").getNickname());
        assertEquals(players.get(2), game.getPlayerByNickname("player3").getNickname());

    }


    @Test
    public void getPlayersNicknames() {
        Game game = new Game(players);
        List<String> playersListFromMethod = game.getPlayersNicknames();
        List<String> realPlayerList = new ArrayList<>();
        realPlayerList.add(players.get(0));
        realPlayerList.add(players.get(1));
        realPlayerList.add(players.get(2));

        assertEquals(playersListFromMethod, realPlayerList);
    }

    @Test
    public void refillClouds() {
        Game game = new Game(players);
        ArrayList<Cloud> clouds = game.getMap().getClouds();
        if(clouds.get(0).getCloudStudents().isEmpty() && clouds.get(1).getCloudStudents().isEmpty())
            game.refillClouds();
        clouds = game.getMap().getClouds();
        assertFalse(clouds.get(0).getCloudStudents().isEmpty());
        assertEquals(4, clouds.get(0).getCloudStudents().size());
        assertFalse(clouds.get(1).getCloudStudents().isEmpty());
        assertEquals(4, clouds.get(1).getCloudStudents().size());
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