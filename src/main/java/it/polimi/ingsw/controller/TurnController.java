package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.extra.ANSICostants;
import it.polimi.ingsw.server.extra.ComparatorAssistantCard;
import it.polimi.ingsw.server.extra.DataSaving;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.server.enumerations.PhaseState.ACTION_PHASE;
import static it.polimi.ingsw.server.enumerations.PhaseState.PLANNING_PHASE;

/**
 * This Class contains all the methods used to manage every single turn of the match.
 * It controls the flow of the match.
 * It is also able to save and restore the game.
 */
public class TurnController implements Serializable {

    private final Game game;
    private final List<String> nicknameQueue;
    private String activePlayer;
    private PhaseState phaseState;
    private ActionPhaseState actionPhaseState;
    private int turnCount = 0;
    private GameController gameController;
    private transient Map<String, VirtualView> virtualViewMap;

    /**
     * Default Constructor of the TurnController.
     */
    public TurnController(GameController gameController, Map<String, VirtualView> virtualViewMap) {
        this.game = gameController.getGame();
        this.nicknameQueue = new ArrayList<>(gameController.getPlayersNicknames());
        this.phaseState = PhaseState.PLANNING_PHASE;

        this.gameController = gameController;
        this.virtualViewMap = virtualViewMap;
    }

    /**
     * Getter method.
     * @return the nickname of the active player.
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * Initialize a new Turn.
     */
    public void newTurn() {
        if(turnCount == 10){
            gameController.winnerChecker();
            return;
        }

        turnCount++;
        System.out.println("Turn: " + turnCount);

        activePlayer = nicknameQueue.get(0);

        if(turnCount > 1 ){
            try {
                DataSaving dataSaving = new DataSaving();
                System.out.println("Salvataggio partita in corso...");
                dataSaving.save(gameController);
            } catch (IOException e) {
                System.out.println(ANSICostants.ANSI_RED + "Errore durante il salvataggio" + ANSICostants.ANSI_RESET);
                e.printStackTrace();
            }
        }
        gameController.showGenericMessageToAll("Turn of " + activePlayer + "...");
        gameController.refillClouds();
        System.out.println("Refill Clouds!");
        gameController.refreshAssistantCard();
        System.out.println("Refresh Assistant Cards!");
        gameController.askAssistantCard();
    }

    /**
     * Set the next activePlayer.
     * Able to understand if the turn of a player is finished.
     */
    public void next() {
        int currentActive = nicknameQueue.indexOf(activePlayer);
        if (currentActive + 1 < nicknameQueue.size()) {
            currentActive = currentActive + 1;
            try{
                game.deleteActiveCard();
            }catch (RuntimeException ignored){
            }
        } else {
            try{
                game.deleteActiveCard();
            }catch (RuntimeException ignored){
            }
            nextPhase();
            return;
        }
        activePlayer = nicknameQueue.get(currentActive);
        if(!game.getPlayerByNickname(activePlayer).isConnected())
            next();

        if(phaseState == PLANNING_PHASE)
            gameController.askAssistantCard();
        else {
            nextActionPhase();
        }
    }

    /**
     * Go to the next phase.
     */
    public void nextPhase() {
        switch (getPhaseState()) {
            case PLANNING_PHASE -> {
                System.out.println("Building nickname queue");
                buildQueue(nicknameQueue);
                phaseState = ACTION_PHASE;
                actionPhaseState = ActionPhaseState.USE_EFFECT;
                actionPhase();
            }
            case ACTION_PHASE -> {
                phaseState = PhaseState.PLANNING_PHASE;
                newTurn();
            }
            default -> System.out.println("error: INVALID PHASE STATE!");
        }
    }

    /**
     * Method that manage what to do during the action phase.
     */
    public void actionPhase() {
        try {
            switch (actionPhaseState) {
                case USE_EFFECT -> {
                    if(game.isExpertMode()){
                        gameController.askCharacterCard();
                    }
                    else nextActionPhase();
                }
                case MOVE_STUDENT1, MOVE_STUDENT2, MOVE_STUDENT3 -> gameController.askToMoveStudent();
                case MOVE_STUDENT4 -> {
                    if(gameController.getChosenPlayerNumber() == 3)
                        gameController.askToMoveStudent();
                    else nextActionPhase();
                }
                case MOVE_MOTHER_NATURE -> gameController.askToMoveMotherNature();
                case PICK_CLOUD -> {
                    gameController.askToChooseACloud();
                    if(game.isExpertMode()){
                        game.disableCardEffects();
                    }
                }
                default -> throw new InvalidActionPhaseStateException();
            }
        }catch(InvalidActionPhaseStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to build the action phase order based on the assistant card chosen by the players.
     * @param playersList list of the online players.
     */
    private void buildQueue(List<String> playersList) {

        List<Player> players = new ArrayList<>();

        for (String s : playersList) {
            Player p = game.getPlayerByNickname(s);
            if(p.isConnected())
                players.add(p);
        }

        players.sort(new ComparatorAssistantCard());

        nicknameQueue.clear();

        for(Player player : players) {
            nicknameQueue.add(player.getNickname());
        }

        activePlayer = nicknameQueue.get(0);
    }

    /**
     * @return the phase state (PLANNING or ACTION).
     */
    public PhaseState getPhaseState(){
        return phaseState;
    }

    /**
     * @return the actionPhase state (USE_EFFECT, MOVE_STUDENT#, MOVE_MOTHER_NATURE, PICK_CLOUD).
     */
    public ActionPhaseState getActionPhaseState(){
        return actionPhaseState;
    }

    /**
     * Used to go to the next actionPhase's state.
     */
    public void nextActionPhase() {
        actionPhaseState = actionPhaseState.next(getActionPhaseState());
        actionPhase();
    }


    /**
     * Setter method of the VirtualViewMap.
     * @param virtualViewMap parameter to be assigned to this.virtualViewMap.
     */
    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {
        this.virtualViewMap = virtualViewMap;
    }

    /**
     * Remove a virtualView associated with the @param nickname.
     * @param nickname nick of the player.
     */
    public void removeVirtualView(String nickname) {
        virtualViewMap.remove(nickname);
    }

    /**
     * Method used to restart the turn based on the actionPhase it was.
     */
    public void restartTurn() {
        if(getPhaseState() == ACTION_PHASE)
            actionPhase();
        else{
            gameController.askAssistantCard();
        }
    }

    /**
     * This method set gameController.
     * @param gameController value to be assigned to this.gameController.
     */
    public void setGameController (GameController gameController) {
        this.gameController = gameController;
    }

    /**
     *  Method that fix the count of the turns after restoring game data from a saved file.
     */
    public void resettingTurnCount() {
        turnCount--;
    }

    /**
     * @return the order of the players playing.
     */
    public List<String> getNicknameQueue() {
        return nicknameQueue;
    }

    /**
     * Setter for the activePlayer
     * @param activePlayer value to be assigned to this.activePlayer.
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }
}
