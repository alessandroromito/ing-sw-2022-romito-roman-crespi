package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.exception.NullCurrentCardException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.Map;

public class InputController {

    private Game game;
    private final GameController gameController;
    private Map<String, VirtualView> virtualViewMap;

    public InputController(GameController gameController, Map<String, VirtualView> virtualViewMap) {
        this.gameController = gameController;
        this.game = gameController.getGame();
        this.virtualViewMap = virtualViewMap;
    }

    /*
    // IDEA IMPLEMENTAZIONE
    public boolean verifyReceivedData(Message message) {

    }
    */

    /**
     * Check if a nickname is free or not.
     *
     * @param nickname new client's nickname.
     * @return {code @true} if it's a valid nickname {code @false} otherwise.
     */
    public boolean checkLoginNickname(String nickname) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Game.SERVER_NAME)) {
            return false;
        } else if (gameController.isNicknameTaken(nickname)) {
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

    public boolean moveCheck(MoveMotherNatureMessage message) throws NullCurrentCardException {
        int steps = message.getSteps();
        Player player = null;
        try {
            player = game.getPlayerByNickname(message.getNickname());
        } catch (MissingPlayerNicknameException e) {

        }

        if(player.getCurrentCard().getMovement() >= steps){
            return true;
        } else {
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.showMessage("You can't move Mother Nature so far!");
            return false;
        }
    }


}
