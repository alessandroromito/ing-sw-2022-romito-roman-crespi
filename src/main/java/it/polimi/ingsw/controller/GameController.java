package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Model;

public class GameController {
    public static final String SAVING = "GameController.sav";
    private Model model;
    private Game game;

    private GameState gameState;
    private TurnController turnController;


    public GameController(Model model, Game game){
        init(model, game);
    }

    public void init(Model model, Game game){
        this.model = model;
        this.game = game;
    }

    private void startGame() throws MissingPlayerNicknameException {

        setGameState(GameState.IN_GAME);
        turnController.newTurn();
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
