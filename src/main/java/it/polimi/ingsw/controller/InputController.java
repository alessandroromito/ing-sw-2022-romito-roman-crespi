package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.NullCurrentCardException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.model.player.Player;

public class InputController {

    public Game game;
    public final Model model;
    public final GameController gameController;


    public InputController(Model model, GameController gameController) {
        this.model = model;
        this.gameController = gameController;
    }

    // IDEA IMPLEMENTAZIONE
    public boolean verifyReceivedData(Message message) {

    }

    /**
     * Check if a nickname is free or not.
     *
     * @param nickname new client's nickname.
     * @return {code @true} if it's a valid nickname {code @false} otherwise.
     */
    public boolean checkLoginNickname(String nickname) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Model.SERVER_NAME)) {
            return false;
        } else if (model.isNicknameTaken(nickname)) {
            return false;
        }
        return true;
    }

    /**
     * Check that the player could move motherNature forward the requested steps
     *
     * @param message received from the client
     * @return {code @true} if he could move {code @false} if not
     */
    public boolean moveCheck(Message message) throws NullCurrentCardException {
        MoveMessage moveMessage = (MoveMessage) message;
        int steps = moveMessage.getSteps();
        Player activePlayer = game.getPlayerByNickname(moveMessage.getNickname());

        if(activePlayer.getCurrentCard().getMovement() >= steps){
            return true;
        } else {
            // Show fail message
            return false;
        }
    }

}
