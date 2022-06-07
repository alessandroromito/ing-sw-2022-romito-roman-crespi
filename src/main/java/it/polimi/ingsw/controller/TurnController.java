package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.model.ExpertGame;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.server.enumerations.PhaseState.ACTION_PHASE;
import static it.polimi.ingsw.server.enumerations.PhaseState.PLANNING_PHASE;

/**
 * This Class contains all the methods used to manage every single turn of the match.
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
        if(turnCount == 10)
            gameController.winnerChecker();

        activePlayer = nicknameQueue.get(0);
        gameController.showGenericMessageToAll("Turn of " + activePlayer + "...");
        // 1
        gameController.refillClouds();
        // 2
        gameController.refreshAssistantCard();
        askAssistantCard();
    }

    private void askAssistantCard() {
        Player player = game.getPlayerByNickname(getActivePlayer());
        List<AssistantCard> assistantCardList = new ArrayList<>(player.getHand());
        VirtualView virtualView = virtualViewMap.get(getActivePlayer());
        virtualView.askAssistantCard(assistantCardList);
    }

    /**
     * Set the next activePlayer.
     */
    public void next() {
        int currentActive = nicknameQueue.indexOf(activePlayer);
        if (currentActive + 1 < nicknameQueue.size()) {
            currentActive = currentActive + 1;
            if(game.getClass().equals(ExpertGame.class)) game.deleteActiveCard();
        } else {
            nextPhase();
            return;
        }
        activePlayer = nicknameQueue.get(currentActive);
        if(phaseState == PLANNING_PHASE)
            askAssistantCard();
        else {
            actionPhase();
        }
    }


    /**
     * Go to the next phase.
     */
    public void nextPhase() {
        switch (getPhaseState()) {
            case PLANNING_PHASE -> {
                buildQueue(nicknameQueue);
                System.out.println("Building nickname queue");
                phaseState = ACTION_PHASE;
                actionPhaseState = ActionPhaseState.MOVE_STUDENT1;
                actionPhase();
            }
            case ACTION_PHASE -> {
                phaseState = PhaseState.PLANNING_PHASE;
                newTurn();
            }
            default -> System.out.println("error: INVALID PHASE STATE!");
        }
    }

    public void actionPhase() {
        try {
            switch (actionPhaseState) {
                case MOVE_STUDENT1, MOVE_STUDENT2, MOVE_STUDENT3 -> gameController.askToMoveStudent();
                case MOVE_MOTHER_NATURE -> gameController.askToMoveMotherNature();
                case PICK_CLOUD -> {
                    gameController.askToChooseACloud();
                    game.disableCardEffects();
                    next();
                }
                default -> throw new InvalidActionPhaseStateException();
            }

        }catch(InvalidActionPhaseStateException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildQueue(List<String> playersList) {
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

    public void nextActionPhase() {
        actionPhaseState = actionPhaseState.next(getActionPhaseState());
        actionPhase();
    }
}
