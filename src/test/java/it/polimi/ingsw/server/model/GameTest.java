package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.DifferentColorTowerException;
import it.polimi.ingsw.server.exception.FullGroupIDListException;
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
    private List<Player> players;
    protected ArrayList<Component> components;

    @Before
    public void setUp() {
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));

        game = new Game(players);
    }

    @Test
    public void CreateComponents(){

        // Print a visual log of the component's ID and class type
        for(int i = 0; i<188; i++) {
            System.out.println("ID: " + game.getComponents().get(i).getID());
            System.out.print(game.getComponents().get(i).getClass().getName());
        }

        assertNotNull(components.get(1));
        assertEquals(MotherNature.class, components.get(1).getClass());

        assertNotNull(components.get(2));
        assertEquals(ProfessorPawn.class, components.get(2).getClass());
        ProfessorPawn p = (ProfessorPawn) components.get(2);
        assertEquals(PawnColors.BLUE, p.getColor());

        assertNotNull(components.get(6));
        assertEquals(ProfessorPawn.class, components.get(6).getClass());
        p = (ProfessorPawn) components.get(6);
        assertEquals(PawnColors.YELLOW, p.getColor());

        assertNotNull(components.get(7));
        assertEquals(Tower.class, components.get(7).getClass());
        Tower t = (Tower) components.get(7);
        assertEquals(TowerColors.BLACK, t.getColor());

    }

    @Test
    public void GameInitialization(){
        Map map = game.getMap();

        assertEquals(map.getMotherNaturePosition(), components.get(1).getPosition().ordinal() - 12);
        components.get(1).setPosition(MapPositions.valueOf("ISLAND_" + 1));
        assertEquals(1, components.get(1).getPosition().ordinal() - 12);

        // Opposite island free
        Island oppositeIsland = map.getIsland(map.getMotherNaturePosition());
        assertArrayEquals(oppositeIsland.getNumberOfColors(), null);

        // Scoreboard entrance is full
        Player p1 = players.get(0);
        Scoreboard scoreboard1 = p1.getScoreboard();

        Player p2 = players.get(1);
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
        assertEquals(6, components.get(1).getPosition().ordinal() - 12);

        map.merge(1,2);


    }

    @Test
    public void getPlayerByNicknameTest(){

    }


}