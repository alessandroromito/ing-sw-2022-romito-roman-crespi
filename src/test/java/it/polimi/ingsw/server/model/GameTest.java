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
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static Game game;
    private List<Player> players;
    protected ArrayList<Component> components;

    @BeforeEach
    void setUp() {
        players.add(new Player());
        players.add(new Player());

        game = new Game(players);
    }

    @Test
    public void CreateComponents(){
        game.createComponents();

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

        assertEquals(MapPositions.ISLANDS, components.get(1).getPositionOnMap());
        components.get(1).setPositionDetailed(1);
        assertEquals(1, components.get(1).getPositionDetailed());

        // Isola opposta a madre natura vuota
        Island oppositeIsland = map.getIsland(components.get(1).getPositionDetailed());
        assertArrayEquals(oppositeIsland.getNumberOfColors(), null);

    }

    @Test
    public void moveMotherNatureTest() throws DifferentColorTowerException, FullGroupIDListException {
        game = new Game(players);
        Map map = game.getMap();
        map.setMotherNaturePos(2);

        game.moveMotherNature(4);

        assertEquals(6, map.getMotherNaturePosition());
        assertEquals(6, components.get(1).getPositionDetailed());

        map.merge(1,2);

    }


}