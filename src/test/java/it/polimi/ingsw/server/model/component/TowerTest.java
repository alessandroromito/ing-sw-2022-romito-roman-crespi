package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.TowerColors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {

    private TowerColors color;
    Tower towerGrey = new Tower(10, TowerColors.GREY);
    Tower towerBlack = new Tower(10, TowerColors.BLACK);
    Tower towerWhite = new Tower(10, TowerColors.WHITE);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getColor() {
        assertEquals(towerBlack.getColor(), TowerColors.BLACK);
        assertEquals(towerGrey.getColor(), TowerColors.GREY);
        assertEquals(towerWhite.getColor(), TowerColors.WHITE);

    }
}