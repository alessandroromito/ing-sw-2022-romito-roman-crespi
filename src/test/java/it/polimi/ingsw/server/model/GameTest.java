package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Cloud;
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
    Game game;
    Map map;
    Bag bag;

    Player teo;
    Player gio;
    Player ale;

    TurnController turnController;
    GameController gameController;

    private final List<String> playersNicknames = new ArrayList<>();

    protected ArrayList<Component> components;

    @Before
    public void setUp() {
        gameController = new GameController();

        String player1 = "teo";
        String player2 = "ale";
        String player3 = "gio";

        gameController.getPlayersNicknames().add(player1);
        gameController.getPlayersNicknames().add(player2);
        gameController.getPlayersNicknames().add(player3);

        playersNicknames.add(player1);
        playersNicknames.add(player2);
        playersNicknames.add(player3);

        game = new ExpertGame(playersNicknames);
        turnController = new TurnController(gameController, gameController.getVirtualViewMap());
        game.setExpertMode(true);
        game.setTurnController(turnController);
        turnController.setActivePlayer(player1);

        this.components = game.getComponents();
        this.map = game.getMap();
        this.bag = game.getBag();

        this.teo = game.getPlayerByNickname("teo");
        this.ale = game.getPlayerByNickname("ale");
        this.gio = game.getPlayerByNickname("gio");
    }

    @Test
    public void CreateComponents(){

        game.createComponents();
        components = game.getComponents();

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
        Island oppositeIsland = map.getIsland(map.getMotherNaturePosition());

        for(Integer integer : map.getIsland(oppositeIsland.getID()).getNumberOfColors())
        {
            assertEquals(0, (int) integer);
        }
        Integer[] numberOfColors = {0,0,0,0,0};
        assertArrayEquals(oppositeIsland.getNumberOfColors(), numberOfColors);

        // Scoreboard entrance is full
        Player p1 = null;
        p1 = game.getPlayerByNickname("teo");
        Scoreboard scoreboard1 = p1.getScoreboard();

        Player p2 = game.getPlayerByNickname("ale");
        Scoreboard scoreboard2 = p2.getScoreboard();

        Player p3 = game.getPlayerByNickname("ale");
        Scoreboard scoreboard3 = p3.getScoreboard();

        assertEquals(9, scoreboard1.getNumStudentsFromEntrance());
        assertEquals(9, scoreboard2.getNumStudentsFromEntrance());
        assertEquals(9, scoreboard3.getNumStudentsFromEntrance());

    }

    @Test
    public void moveMotherNatureTest() {
        map.setMotherNaturePos(1);
        game.moveMotherNature(3);
        assertEquals(map.getMotherNaturePosition(), 4);

        map.setMotherNaturePos(11);
        game.moveMotherNature(3);
        assertEquals(map.getMotherNaturePosition(), 2);
    }

    @Test
    public void getPlayerByNicknameTest() {

        assertEquals(game.getPlayers().get(0).getNickname(), game.getPlayerByNickname("teo").getNickname());
        assertEquals(game.getPlayers().get(1).getNickname(), game.getPlayerByNickname("ale").getNickname());
        assertEquals(game.getPlayers().get(2).getNickname(), game.getPlayerByNickname("gio").getNickname());
    }


    @Test
    public void getPlayersNicknames() {
        List<String> playersListFromMethod = game.getPlayersNicknames();
        List<String> realPlayerList = new ArrayList<>();
        realPlayerList.add(game.getPlayers().get(0).getNickname());
        realPlayerList.add(game.getPlayers().get(1).getNickname());
        realPlayerList.add(game.getPlayers().get(2).getNickname());

        assertEquals(playersListFromMethod, realPlayerList);
    }

    @Test
    public void refillClouds() {
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
        AssistantCard assistantCard = new AssistantCard(4, 4, 2);

        game.setAssistantCard("teo", 4);

        assertEquals(game.getPlayerByNickname("teo").getCurrentCard().getID(), assistantCard.getID());
        assertEquals(game.getPlayerByNickname("teo").getCurrentCard().getValue(), assistantCard.getValue());
        assertEquals(game.getPlayerByNickname("teo").getCurrentCard().getMovement(), assistantCard.getMovement());

        game.getPlayerByNickname("teo").resetAssistantCard();

        for(AssistantCard card : game.getPlayerByNickname("teo").getHand()){
            assertNotEquals(4, card.getID());
        }
    }

    @Test
    public void moveStudentToIsland() {
        StudentDisc studentDisc = bag.getBagStudents().get(0);

        game.moveStudentToIsland(studentDisc, 4);

        assertTrue(map.getIsland(4).getStudents().contains(studentDisc));
    }

    @Test
    public void moveTowerToIsland() {
        Tower tower = game.getPlayerByNickname("teo").getScoreboard().removeTower();

        game.moveTowerToIsland(tower, 4);

        assertTrue(map.getIsland(4).getTowers().contains(tower));
        assertEquals(TowerColors.BLACK, map.getIsland(4).getTowerColor());

        assertEquals(5, game.getPlayerByNickname("teo").getScoreboard().getNumTowers());
    }

    @Test
    public void moveProfessor() {
        game.getPlayerByNickname("ale").getScoreboard().addProfessor(new ProfessorPawn(0, PawnColors.PINK));

        game.moveProfessor(PawnColors.PINK, teo);

        assertTrue(teo.getScoreboard().getProfessor(PawnColors.PINK));
        assertFalse(game.getPlayerByNickname("ale").getScoreboard().getProfessor(PawnColors.PINK));
    }

    @Test
    public void moveStudentToDiningRoom() {
        StudentDisc studentDisc = teo.getScoreboard().getEntrance().get(0);

        game.moveStudentToDiningRoom(studentDisc);

        assertEquals(1, teo.getScoreboard().getPlayerStudentFromDining(studentDisc.getColor()));
        assertFalse(teo.getScoreboard().getEntrance().contains(studentDisc));
    }

    @Test
    public void pickAndPlaceFromCloud() {
        Cloud cloud = game.getMap().getCloud(1);
        ArrayList<StudentDisc> studentsOnCloud = cloud.getCloudStudents();

        game.pickAndPlaceFromCloud(1);

        for(StudentDisc student: cloud.getCloudStudents()){
            assertTrue(game.getActivePlayer().getScoreboard().getEntrance().contains(student));
        }

    }

    @Test
    public void checkInfluence(){
        teo.getScoreboard().addProfessor(new ProfessorPawn(1, PawnColors.RED));
        System.out.println("teo: " + teo.getScoreboard().getNumProf());

        map.getIsland(4).addStudent(new StudentDisc(1,PawnColors.RED));
        map.getIsland(4).addStudent(new StudentDisc(1,PawnColors.RED));

        game.checkInfluence(4);

        assertSame(map.getIsland(4).getTowerColor(), teo.getScoreboard().getTowerColor());

        map.getIsland(5).addStudent(new StudentDisc(1,PawnColors.RED));
        map.getIsland(5).addStudent(new StudentDisc(1,PawnColors.RED));

        game.checkInfluence(5);

        assertTrue(map.getIsland(4).isDisabled());
        assertTrue(map.getIsland(5).isDisabled());
    }

    @Test
    public void getComponentByIDTest() {
        assertEquals(components.get(0), game.getComponent(1));
        assertEquals(components.get(1), game.getComponent(2));
    }
}