package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.exception.WrongPhaseStateException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.enumerations.PhaseState.ACTION_PHASE;

/**
 * This Class contains all the methods used to manage every single turn of the match.
 */
public class TurnController {

    private final Model model;
    private final Game game;
    private final List<String> nicknameQueue;
    private String activePlayer;

    private PhaseState phaseState;
    private ActionPhaseState actionPhaseState;

    private final GameController gameController;

    /**
     * Default Constructor of the TurnController
     */
    public TurnController(GameController gameController) {
        this.gameController = gameController;
        this.model = gameController.getModel();
        this.game = model.getGame();
        this.nicknameQueue = new ArrayList<>(game.getPlayersNicknames());
        this.phaseState = PhaseState.PLANNING_PHASE;
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
    public void newTurn() throws MissingPlayerNicknameException {
        activePlayer = nicknameQueue.get(0);
        // 1
        game.refillClouds();
        // 2
        try {
            if(getPhaseState() == PhaseState.PLANNING_PHASE) gameController.askAllToChooseAssistantCard();
            else throw new WrongPhaseStateException();
        } catch (WrongPhaseStateException e) {
            e.printStackTrace();
        }
    }





    /**
     * Set the next activePlayer.
     */
    public void next() throws MissingPlayerNicknameException {

        int currentActive = nicknameQueue.indexOf(activePlayer);
        if (currentActive + 1 < model.getNumberOfPlayer()) {
            currentActive = currentActive + 1;
        } else {
            nextPhase();
            return;
        }
        activePlayer = nicknameQueue.get(currentActive);
    }


    /**
     * Go to the next phase.
     */
    public void nextPhase() throws MissingPlayerNicknameException {
        switch (getPhaseState()) {
            case PLANNING_PHASE -> {
                buildQueue(nicknameQueue);
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

    public void actionPhase() throws MissingPlayerNicknameException, InvalidActionPhaseStateException {
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

        //da testare
        players.sort(new ComparatorAssistantCard());


        nicknameQueue.clear();

        for(Player player : players) {
            nicknameQueue.add(player.getNickname());
        }
    }

    public PhaseState getPhaseState(){
        return phaseState;
    }

    public ActionPhaseState getActionPhaseState(){return actionPhaseState;}

    public List<String> getNicknameQueue() { return this.nicknameQueue; }
}
