package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.CloudNotEmptyException;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.ExpertGame;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.server.enumerations.PhaseState.ACTION_PHASE;

/**
 * This Class contains all the methods used to manage every single turn of the match.
 */
public class TurnController {

    private final Game game;
    private final List<String> nicknameQueue;
    private String activePlayer;
    private boolean lastTurn = false;

    private PhaseState phaseState;
    private ActionPhaseState actionPhaseState;

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
     * @param activePlayer the active Player to be set.
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Initialize a new Turn.
     */
    public void newTurn() {
        activePlayer = nicknameQueue.get(0);
        gameController.showGenericMessage("Turn of " + activePlayer + "...");
        // 1
        gameController.refillClouds();
        // 2
        askAssistantCard();
    }

    private void askAssistantCard() {
        for(String nickname : nicknameQueue){
            Player player = null;
            try {
                player = game.getPlayerByNickname(getActivePlayer());
                List<AssistantCard> assistantCardLists = new ArrayList<>(player.getHand());
                VirtualView virtualView = virtualViewMap.get(getActivePlayer());

                if (assistantCardLists.isEmpty()) {
                    gameController.winnerChecker();
                } else {
                    virtualView.askAssistantCard(getActivePlayer(), assistantCardLists);
                }
            } catch (MissingPlayerNicknameException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the next activePlayer.
     */
    public void next() throws MissingPlayerNicknameException, InvalidActionPhaseStateException, CloudNotEmptyException {

        int currentActive = nicknameQueue.indexOf(activePlayer);
        if (currentActive + 1 < gameController.getPlayersNumber()) {
            currentActive = currentActive + 1;
            if(game.getClass().equals(ExpertGame.class)) game.deleteActiveCard();
        } else {
            nextPhase();
            return;
        }
        activePlayer = nicknameQueue.get(currentActive);
    }


    /**
     * Go to the next phase.
     */
    public void nextPhase() throws MissingPlayerNicknameException, InvalidActionPhaseStateException, CloudNotEmptyException {
        switch (getPhaseState()) {
            case PLANNING_PHASE -> {
                buildQueue(nicknameQueue);
                phaseState = ACTION_PHASE;
                actionPhaseState = ActionPhaseState.MOVE_STUDENT1;
                actionPhase();
            }
            case ACTION_PHASE -> {
                if(lastTurn){
                    gameController.winnerChecker();
                    return;
                }
                phaseState = PhaseState.PLANNING_PHASE;
                newTurn();
            }
            default -> System.out.println("error: INVALID PHASE STATE!");
        }
    }

    public void actionPhase() throws MissingPlayerNicknameException, InvalidActionPhaseStateException, CloudNotEmptyException {
        switch (actionPhaseState) {
            case MOVE_STUDENT1, MOVE_STUDENT2, MOVE_STUDENT3 -> {
                gameController.askToMoveStudent();
                //quando arriva il messaggio di fine mossa aggiorna actionPhaseState (con metodo actionPhaseState.next())
                //ci sarà un onUpdate che chiamerà actionPhase()
            }
            case MOVE_MOTHER_NATURE -> {
                gameController.askToMoveMotherNature();
                //come sopra
            }
            case PICK_CLOUD -> {
                gameController.askToChooseACloud();
                //come sopra
                next();
            }
            default -> throw new InvalidActionPhaseStateException();
        }

        if(getPhaseState() == ACTION_PHASE) actionPhase();
    }

    private void buildQueue(List<String> playersList) throws MissingPlayerNicknameException {
        List<Player> players = new ArrayList<>();

        for (String s : playersList) {
            Player p = game.getPlayerByNickname(s);
            players.add(p);
        }

        //to be tested
        players.sort(new ComparatorAssistantCard());

        nicknameQueue.clear();

        for(Player player : players) {
            nicknameQueue.add(player.getNickname());
        }
    }

    public PhaseState getPhaseState(){
        return phaseState;
    }

    public ActionPhaseState getActionPhaseState(){
        return actionPhaseState;
    }

    public List<String> getNicknameQueue() {
        return this.nicknameQueue;
    }

    public void setLastTurn() {
        this.lastTurn = true;
    }
}
