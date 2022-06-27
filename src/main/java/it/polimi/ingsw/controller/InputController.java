package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.network.message.UseEffectMessage;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
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

    /**
     * Check if a nickname is free or not.
     *
     * @param nickname new client's nickname.
     * @return {code @true} if it's a valid nickname {code @false} otherwise.
     */
    public boolean checkLoginNickname(String nickname) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Game.SERVER_NAME)) {
            return false;
        } else return !gameController.isNicknameTaken(nickname);
    }

    public boolean playerNumberReplyCheck(int chosenPlayerNumber) {
        if (chosenPlayerNumber < 4 && chosenPlayerNumber> 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check that the player could move motherNature forward the requested steps
     *
     * @param message received from the client
     * @return {code @true} if he could move {code @false} if not
     */
    public boolean moveCheck(MoveMotherNatureMessage message) {
        int steps = message.getSteps();
        Player player;
        player = game.getPlayerByNickname(message.getNickname());

        if (player.getCurrentCard().getMovement() >= steps) {
            return true;
        }else if(game.getActiveCardID() == 212) {
            return player.getCurrentCard().getMovement() + 2 >= steps;
        }
        else{
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.showMessage(message.getNickname(), "You can't move Mother Nature so far!");
            return false;
        }
    }

    /**
     *
     * @param card the player's card he wants to choose
     * @return true if it's valid or false if it's not
     */
    public boolean validateCard(AssistantCard card) {
        for(Player player: game.getPlayers()){
            if(!(player.getNickname().equals(game.getActivePlayer().getNickname())) && player.getCurrentCard() != null) {
                if(player.getCurrentCard().getValue() == card.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check that the player have enough coin
     *
     * @param message
     * @return
     */
    public boolean checkCoin(UseEffectMessage message) {
        int cost = game.getCharacterCardByID(message.getCardID()).getCost();
        Player player = game.getPlayerByNickname(message.getNickname());

        return player.getCoin() >= cost;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {
        this.virtualViewMap = virtualViewMap;
    }

    public void removeVirtualView(String nickname) {
        virtualViewMap.remove(nickname);
    }
}
