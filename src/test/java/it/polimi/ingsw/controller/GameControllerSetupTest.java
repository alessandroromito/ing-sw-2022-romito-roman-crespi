package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerSetupTest {

    private List<String> players;
    private Map<String, VirtualView> virtualViewMap;

    private int chosenPlayerNumber = 0;
    private boolean chosenExpertMode = false;

    private Game game;

    private GameState gameState;
    private TurnController turnController;
    private InputController inputController;
    private GameController gameController;

    @BeforeEach
    void setUp() {

        this.players = new ArrayList<String>();
        this.gameController = new GameController();
        this.virtualViewMap = new HashMap<>();
        this.inputController = new InputController(gameController, virtualViewMap);
        this.gameState = GameState.GAME_ROOM;

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void init() {
        assertNotEquals(null,this.inputController);
        assertNotEquals(null,this.virtualViewMap);
    }

    @Test
    void addPlayer() {
        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);

        this.game = new Game(players);
        this.chosenPlayerNumber = 3;
        assertEquals(3, game.getNumberOfPlayer());
    }

    @Test
    void isGameStarted() {
        assertEquals(GameState.GAME_ROOM,this.gameState);
    }

    @Test
    void getPlayersNicknames() {
        String player1 = "Player1";
        String player2 = "Player2";
        String player3 = "Player3";

        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);

        Game game = new Game(players);

        assertEquals(game.getPlayers().get(0).getNickname(),"Player1");
        assertEquals(game.getPlayers().get(1).getNickname(),"Player2");
        assertEquals(game.getPlayers().get(2).getNickname(),"Player3");
    }

    @Test
    void isNicknameTaken() {
        String player1 = "Player1";
        String player2 = "Player2";
        String player3 = "Player3";

        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);

        Game game = new Game(players);

        List<String> playerList = game.getPlayersNicknames();

        for(String playerName : playerList)
            if(playerName.equals("Player2")) {
                assertTrue(true);
                return;
            }
        fail();

        //assertFalse(inputController.checkLoginNickname(game.getPlayersNicknames().get(0)));
    }

}

