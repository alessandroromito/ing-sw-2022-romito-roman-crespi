package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Model;

public class GameController {
    private Model model;
    private Game game;

    private GameState gameState;
    private TurnController turnController;


    public GameController(){
        init();
    }

    public void init(){
        this.model = Model.getModel();
        this.game = model.getGame();
    }

    private void startGame() throws MissingPlayerNicknameException {

        setGameState(GameState.IN_GAME);
        turnController.newTurn();
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
