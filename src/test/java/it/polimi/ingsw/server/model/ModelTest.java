package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.GameAlreadyStartedException;
import it.polimi.ingsw.server.exception.MaxPlayerException;
import it.polimi.ingsw.server.exception.MissingPlayersException;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    private static Model model;

    private List<Player> players;

    private boolean gameStarted;
    private boolean endGame;

    int playerNumber;

    @BeforeEach
    void setUp() {
        model = new Model();
        this.model = Model.getModel();
    }

    @Test
    void getModel() {
        assertFalse(model.isGameStarted());
        assertEquals(model, Model.getModel());
    }

    @Test
    void addPlayer() throws MissingPlayersException {
        assertNull(players);
        model.addPlayer(new Player());

        assertEquals(1, playerNumber);
        assertEquals(1, players.size());


        model.addPlayer(new Player());

        //gameStarted = true;
        //assertThrows(GameAlreadyStartedException.class, () -> model.addPlayer(new Player()));
        //gameStarted = false;

        model.addPlayer(new Player());
        model.addPlayer(new Player());

        assertEquals(3, playerNumber);
        assertThrows(MaxPlayerException.class, () -> model.addPlayer(new Player()));
        assertThrows(NullPointerException.class, () -> model.addPlayer(null));

    }

    @Test
    void startGame() throws MissingPlayersException {
        model.addPlayer(new Player());
        model.addPlayer(new Player());
        model.startGame();
        assertTrue(model.isGameStarted());
        assertThrows(GameAlreadyStartedException.class, () -> model.startGame());
    }

}