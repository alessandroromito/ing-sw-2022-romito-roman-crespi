package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.network.message.UseEffectMessage;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Class that contains methods about check and validation of the messages sent by client side.
 */
public class InputController implements Serializable {
    @Serial
    private static final long serialVersionUID = -7360071799215316251L;

    private Game game;
    private final GameController gameController;
    private transient Map<String, VirtualView> virtualViewMap;

    public InputController(GameController gameController, Map<String, VirtualView> virtualViewMap) {
        this.gameController = gameController;
        this.game = gameController.getGame();
        this.virtualViewMap = virtualViewMap;
    }

    /**
     * Check if a nickname is free or not.
     * @param nickname new client's nickname.
     * @return {code @true} if it's a valid nickname {code @false} otherwise.
     */
    public boolean checkLoginNickname(String nickname) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Game.SERVER_NAME)) {
            return false;
        } else return !gameController.isNicknameTaken(nickname);
    }

    /**
     * Check if the player number is valid or not.
     * @param chosenPlayerNumber player number chosen by the client.
     * @return {code @true} if it is ok, {code @false} otherwise.
     */
    public boolean playerNumberReplyCheck(int chosenPlayerNumber) {
        return chosenPlayerNumber < 4 && chosenPlayerNumber > 1;
    }

    /**
     * Check that the player could move motherNature forward the requested steps.
     * @param message received from the client.
     * @return {code @true} if he could move {code @false} otherwise.
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
            virtualView.showMessage("Non puoi spostare madre nature così lontano!");
            return false;
        }
    }

    /**
     * Check if the assistant card chosen by the client can be used or not.
     * @param card the player's card he wants to choose.
     * @return {code @true} if it's valid or {code @false} otherwise.
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
     * Check that the player have enough coin to use effects.
     * @param message type of effect to use.
     * @return {code @true} if it is ok, {code @false} otherwise.
     */
    public boolean checkCoin(UseEffectMessage message) {
        int cost = game.getCharacterCardByID(message.getCardID()).getCost();
        Player player = game.getPlayerByNickname(message.getNickname());

        return player.getCoin() >= cost;
    }

    /**
     * Set Game.
     * @param game parameter to set this.game.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Set virtualViewMap.
     * @param virtualViewMap parameter to set this.virtualViewMap.
     */
    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {
        this.virtualViewMap = virtualViewMap;
    }

    /**
     * Remove the virtualView from a client.
     * @param nickname nickname of the client.
     */
    public void removeVirtualView(String nickname) {
        virtualViewMap.remove(nickname);
    }
}
