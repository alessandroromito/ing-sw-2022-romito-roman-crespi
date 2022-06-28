package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.extra.ComparatorAssistantCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.server.enumerations.PhaseState.ACTION_PHASE;
import static it.polimi.ingsw.server.enumerations.PhaseState.PLANNING_PHASE;

/**
 * This Class contains all the methods used to manage every single turn of the match.
 * It controls the flow of the match.
 */
public class TurnController {

    private final Game game;
    private final List<String> nicknameQueue;

    private String activePlayer;

    private PhaseState phaseState;
    private ActionPhaseState actionPhaseState;
    private int turnCount = 0;

    private final GameController gameController;

    private Map<String, VirtualView> virtualViewMap;

    /**
     * Default Constructor of the TurnController
     */
    public TurnController(GameController gameController, Map<String, VirtualView> virtualViewMap) {
        this.game = gameController.getGame();
        this.nicknameQueue = new ArrayList<>(gameController.getPlayersNicknames());
        this.phaseState = PhaseState.PLANNING_PHASE;

        this.gameController = gameController;
        this.virtualViewMap = virtualViewMap;
    }

    /**
     * @return the nickname of the active player.
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * Initialize a new Turn.
     */
    public void newTurn() {
        turnCount++;
        System.out.println("Turn: " + turnCount);
        if(turnCount == 10)
            gameController.winnerChecker();

        activePlayer = nicknameQueue.get(0);

        if( turnCount == 1 ){

            try {
                GameController gameController = this.gameController.getDataSaving().restore();
                if (nicknameQueue.equals(gameController.getTurnController().getNicknameQueue())) {
                    System.out.println("Caricamento salvataggio di gioco.");
                    restartTurn(nicknameQueue.get(0));
                    return;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Nessun salvataggio di gioco presente.");
            }

        }


        //SAVE THE GAME??????????????????????????????????????????????????????????????????????????????
        if( turnCount > 1 ){
            try {
                System.out.println("Salvataggio partita in corso...");
                gameController.getDataSaving().save(gameController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gameController.showGenericMessageToAll("Turn of " + activePlayer + "...");
        // 1
        gameController.refillClouds();
        System.out.println("Refill Clouds!");
        // 2
        gameController.refreshAssistantCard();
        System.out.println("Refresh Assistant Cards!");

        gameController.askAssistantCard();
    }

    /**
     * Set the next activePlayer, check if the turn has ended.
     */
    public void next() {
        int currentActive = nicknameQueue.indexOf(activePlayer);
        if (currentActive + 1 < nicknameQueue.size()) {
            currentActive = currentActive + 1;
            game.deleteActiveCard();
        } else {
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
     * Method use to perform the one of the action phase actions
     * through a switch case used to save the state
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
                    game.disableCardEffects();
                }
                default -> throw new InvalidActionPhaseStateException();
            }
        }catch(InvalidActionPhaseStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to build the action phase order based on the assistant card
     * chosen by the players
     *
     * @param playersList list of the online players
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
     * @return the phase state (PLANNING or ACTION)
     */
    public PhaseState getPhaseState(){
        return phaseState;
    }

    /**
     * @return the actionPhase state (USE_EFFECT, MOVE_STUDENT#, MOVE_MOTHER_NATURE, PICK_CLOUD)
     */
    public ActionPhaseState getActionPhaseState(){
        return actionPhaseState;
    }

    /**
     * Used to go to the next actionPhase's state
     */
    public void nextActionPhase() {
        actionPhaseState = actionPhaseState.next(getActionPhaseState());
        actionPhase();
    }

    /**
     * Setter of the virtualViewMap
     */
    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {
        this.virtualViewMap = virtualViewMap;
    }

    /**
     * Remove a virtualView associated with the @param nickname
     * @param nickname nick of the player
     */
    public void removeVirtualView(String nickname) {
        virtualViewMap.remove(nickname);
    }

    /**
     * Method used to restart the turn based on the actionPhase it was
     * @param nickname active player nickname
     */
    public void restartTurn(String nickname) {
        if(getPhaseState() == ACTION_PHASE)
            actionPhase();
        else{
            gameController.askAssistantCard();
        }
    }

    /**
     * Setter for the activePlayer
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }
}
