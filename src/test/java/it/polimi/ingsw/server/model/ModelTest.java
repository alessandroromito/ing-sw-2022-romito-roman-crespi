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

    }

    @Test
    void addPlayer() throws MissingPlayersException {
        assertNull(players);
        model.addPlayer(new Player("Player 1"));

        assertEquals(1, playerNumber);
        assertEquals(1, players.size());


        model.addPlayer(new Player("Player 2"));

        //gameStarted = true;
        //assertThrows(GameAlreadyStartedException.class, () -> model.addPlayer(new Player()));
        //gameStarted = false;

        model.addPlayer(new Player("Player 3"));
        model.addPlayer(new Player("Player 4"));

        assertEquals(3, playerNumber);
        assertThrows(MaxPlayerException.class, () -> model.addPlayer(new Player("Player 5")));
        assertThrows(NullPointerException.class, () -> model.addPlayer(null));

    }

    @Test
    void startGame() throws MissingPlayersException {
        model.addPlayer(new Player("player 1"));
        model.addPlayer(new Player("Player 2"));
        model.startGame();

        assertThrows(GameAlreadyStartedException.class, () -> model.startGame());
    }

}